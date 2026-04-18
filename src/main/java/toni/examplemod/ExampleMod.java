package toni.examplemod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

#if FABRIC
import net.fabricmc.api.ModInitializer;
#endif

#if FORGE
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
#endif

#if NEO
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
#endif

#if FORGELIKE
@Mod("example_mod")
#endif
public class ExampleMod #if FABRIC implements ModInitializer #endif {

    public static final String ID = "example_mod";
    public static final Logger LOGGER = LogManager.getLogger(ID);

    public ExampleMod(#if NEO IEventBus modEventBus, ModContainer modContainer #endif) {
        #if FORGE
        var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        #endif

        #if FORGELIKE
        modEventBus.addListener(this::commonSetup);
        #endif
    }

    #if FABRIC @Override #endif
    public void onInitialize() {
        LOGGER.info("Hello from {}!", ID);
    }

    #if FORGELIKE
    public void commonSetup(FMLCommonSetupEvent event) { onInitialize(); }
    #endif
}
