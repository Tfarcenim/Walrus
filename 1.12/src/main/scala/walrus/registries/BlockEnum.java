package walrus.registries;

import walrus.block.BlockWalrus;
import walrus.integration.Integration;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

import java.util.function.Function;

public enum BlockEnum {
	WALRUS("walrus", new BlockWalrus());

	private Block block;
	private ItemBlock item;
	private Integration.Mods mod;

	BlockEnum(String internalName, Block block) {
		this(internalName, block, ItemBlock::new);
	}

	BlockEnum(String internalName, Block block, Function<Block, ItemBlock> itemFactory) {
		this(internalName, block, itemFactory, null);
	}

	BlockEnum(String internalName, Block block, Function<Block, ItemBlock> factory, Integration.Mods mod) {
		this.block = block;
		this.block.setUnlocalizedName("walrus.block." + internalName);
		this.block.setRegistryName(internalName);
		this.item = factory.apply(block);
		this.item.setRegistryName(block.getRegistryName());
		this.mod = mod;
		if (mod == null || mod.isEnabled()) {
			this.block.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		}
	}

	public Block getBlock() {
		return this.block;
	}

	public ItemBlock getItem() {
		return item;
	}

}
