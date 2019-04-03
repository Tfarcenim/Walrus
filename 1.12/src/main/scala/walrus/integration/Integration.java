package walrus.integration;

import walrus.ExtraCells;
import walrus.util.Log;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModAPIManager;
import net.minecraftforge.fml.relauncher.Side;

public class Integration {

	public enum Mods {
		JEI("jei", "Just Enough Items", Side.CLIENT);

		private final String modID;

		private boolean shouldLoad = true;

		private final String name;

		private final Side side;

		Mods(String modid, String modName, Side side) {
			this.modID = modid;
			this.name = modName;
			this.side = side;
		}

		public String getModID() {
			return modID;
		}

		public boolean isOnClient() {
			return side != Side.SERVER;
		}

		public boolean isOnServer() {
			return side != Side.CLIENT;
		}

		public boolean isEnabled() {
			return (Loader.isModLoaded(getModID()) && shouldLoad && correctSide()) || (ModAPIManager.INSTANCE.hasAPI(getModID()) && shouldLoad && correctSide());
		}

		private boolean correctSide() {
			return ExtraCells.proxy.isClient() ? isOnClient() : isOnServer();
		}
	}


	public void preInit() {
		for(Mods mod : Mods.values()){
			if(mod.isEnabled())
				Log.info("Enable integration for '" + mod.name + " (" + mod.modID + ")'");
		}
	}

	public void init() {
	}

	public void postInit() {
		}

}
