package walrus.registries;

import walrus.integration.Integration;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public enum ItemEnum {
;

	private Item item;
	private Integration.Mods mod;

	ItemEnum(String internalName, Item item, Integration.Mods mod, CreativeTabs creativeTab) {
		this.item = item;
		this.item.setUnlocalizedName("walrus." + internalName);
		this.item.setRegistryName(internalName);
		this.mod = mod;
		if ((creativeTab != null) && (mod == null || mod.isEnabled())) {
			this.item.setCreativeTab(creativeTab);
		}
	}

	public Item getItem() {
		return this.item;
	}

	public boolean shouldRegister() {
		return mod == null || mod.isEnabled();
	}
}
