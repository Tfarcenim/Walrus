package walrus.models;

import com.google.common.base.Preconditions;
import walrus.block.IColoredBlock;
import walrus.item.IColoredItem;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class ModelManager {

	private static final ModelManager INSTANCE = new ModelManager();

	/* CUSTOM MODELS*/
	private static final Map<String, IModel> customModels = new HashMap<>();
	/* ITEM AND BLOCK REGISTERS*/
	private static final List<IItemModelRegister> itemModelRegisters = new ArrayList<>();
	private static final List<IStateMapperRegister> stateMapperRegisters = new ArrayList<>();
	private static final List<IColoredBlock> blockColorList = new ArrayList<>();
	private static final List<IColoredItem> itemColorList = new ArrayList<>();

	public void registerItemModel(Item item, int meta) {
		ModelLoader.setCustomModelResourceLocation(item, meta, getModelLocation(item));
	}

	public ModelResourceLocation getModelLocation(Item item) {
		ResourceLocation resourceLocation = item.getRegistryName();
		Preconditions.checkNotNull(resourceLocation);
		String itemName = resourceLocation.getResourcePath();
		return getModelLocation(itemName);
	}

	public ModelResourceLocation getModelLocation(String identifier) {
		return getModelLocation("walrus", identifier);
	}

	public ModelResourceLocation getModelLocation(String modID, String identifier) {
		return new ModelResourceLocation(modID + ":" + identifier, "inventory");
	}

	public static void registerBlockClient(Block block) {
		if (block instanceof IItemModelRegister) {
			itemModelRegisters.add((IItemModelRegister) block);
		}
		if (block instanceof IStateMapperRegister) {
			stateMapperRegisters.add((IStateMapperRegister) block);
		}
		if (block instanceof IColoredBlock) {
			blockColorList.add((IColoredBlock) block);
		}
	}

	public static void registerItemClient(Item item) {
		if (item instanceof IItemModelRegister) {
			itemModelRegisters.add((IItemModelRegister) item);
		}
		if (item instanceof IColoredItem) {
			itemColorList.add((IColoredItem) item);
		}
	}

	public static void registerModels() {
		for (IItemModelRegister itemModelRegister : itemModelRegisters) {
			Item item = null;
			if (itemModelRegister instanceof Block) {
				item = Item.getItemFromBlock((Block) itemModelRegister);
			} else if (itemModelRegister instanceof Item) {
				item = (Item) itemModelRegister;
			}

			if (item != null) {
				itemModelRegister.registerModel(item, INSTANCE);
			}
		}

		for (IStateMapperRegister stateMapperRegister : stateMapperRegisters) {
			stateMapperRegister.registerStateMapper();
		}
	}

	public static void registerItemAndBlockColors() {
		Minecraft minecraft = Minecraft.getMinecraft();

		BlockColors blockColors = minecraft.getBlockColors();
		for (IColoredBlock blockColor : blockColorList) {
			if (blockColor instanceof Block) {
				blockColors.registerBlockColorHandler(ColoredBlockBlockColor.INSTANCE, (Block) blockColor);
			}
		}

		ItemColors itemColors = minecraft.getItemColors();
		for (IColoredItem itemColor : itemColorList) {
			if (itemColor instanceof Item) {
				itemColors.registerItemColorHandler(ColoredItemItemColor.INSTANCE, (Item) itemColor);
			}
		}
	}

	public static void init() {
		OBJLoader.INSTANCE.addDomain("walrus");
		ModelLoaderRegistry.registerLoader(new ECModelLoader(customModels));

	}

	public static void onBakeModels(ModelBakeEvent event) {
		//load default item and block model states
	}

	private static class ColoredItemItemColor implements IItemColor {
		public static final ColoredItemItemColor INSTANCE = new ColoredItemItemColor();

		private ColoredItemItemColor() {

		}

		@Override
		public int colorMultiplier(ItemStack stack, int tintIndex) {
			Item item = stack.getItem();
			if (item instanceof IColoredItem) {
				return ((IColoredItem) item).getColorFromItemstack(stack, tintIndex);
			}
			return 0xffffff;
		}
	}

	private static class ColoredBlockBlockColor implements IBlockColor {
		public static final ColoredBlockBlockColor INSTANCE = new ColoredBlockBlockColor();

		private ColoredBlockBlockColor() {

		}

		@Override
		public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
			Block block = state.getBlock();
			if (block instanceof IColoredBlock && worldIn != null && pos != null) {
				return ((IColoredBlock) block).colorMultiplier(state, worldIn, pos, tintIndex);
			}
			return 0xffffff;
		}
	}

}
