package top.fus_lingx.crh.crh;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import top.fus_lingx.crh.crh.common.network.ModMessages;
import top.fus_lingx.crh.crh.server.ServerPayloadHandler;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(value = Crh.MODID)
public class Crh {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "crh";

    public Crh(IEventBus bus) {
        bus.addListener(Crh::onPayloadRegister);
    }

    @SubscribeEvent
    public static void onPayloadRegister(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1.1");

        registrar.playToServer(
            ModMessages.EncasingNetWork.TYPE,
            ModMessages.EncasingNetWork.STREAM_CODEC,
            ServerPayloadHandler::ServerEncase
        );
    }
}

