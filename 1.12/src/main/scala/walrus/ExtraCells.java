package walrus;

import walrus.integration.Integration;
import walrus.proxy.CommonProxy;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Constants.MOD_ID, version = Constants.VERSION, name = "Walrus", dependencies = "after:waila;")
public class ExtraCells {
	@SidedProxy(clientSide = "walrus.proxy.ClientProxy", serverSide = "walrus.proxy.CommonProxy")
	public static CommonProxy proxy = null;

	public static final Integration integration = new Integration();

	public ExtraCells() {
		FluidRegistry.enableUniversalBucket();
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		integration.preInit();
		proxy.registerItems();
		proxy.registerBlocks();
		proxy.registerModels();

		//Moved to preeinit for JSON recipes
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.registerMovables();
		proxy.registerRenderers();
		proxy.registerTileEntities();
		integration.init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		integration.postInit();

	}
}
