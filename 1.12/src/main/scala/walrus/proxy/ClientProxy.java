package walrus.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import walrus.models.ModelManager;

@SuppressWarnings("unused")
public class ClientProxy extends CommonProxy {

	public ClientProxy() {
		super();
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void registerRenderers() {
		ModelManager.registerItemAndBlockColors();
	}

	@SubscribeEvent
	public void onBakeModels(ModelBakeEvent event) {
		ModelManager.onBakeModels(event);
	}

	@Override
	public void registerModels() {
		ModelManager.init();
		ModelManager.registerModels();
	}

	@Override
	public void registerBlock(Block block) {
		super.registerBlock(block);
		ModelManager.registerBlockClient(block);
	}

	@Override
	public void registerItem(Item item) {
		super.registerItem(item);
		ModelManager.registerItemClient(item);
	}

	@Override
	public boolean isClient() {
		return true;
	}
}
