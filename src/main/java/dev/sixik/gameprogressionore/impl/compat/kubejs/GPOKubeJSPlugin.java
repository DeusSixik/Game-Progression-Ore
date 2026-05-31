package dev.sixik.gameprogressionore.impl.compat.kubejs;

import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingRegistry;

public final class GPOKubeJSPlugin implements KubeJSPlugin {

    @Override
    public void registerBindings(BindingRegistry bindings) {
        if(bindings.type().isServer()) {
            bindings.add("GPOre", GPOKubeJS.class);
        }
    }
}
