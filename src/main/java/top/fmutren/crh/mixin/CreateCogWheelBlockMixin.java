package top.fmutren.crh.mixin;

import com.simibubi.create.content.decoration.encasing.EncasableBlock;
import com.simibubi.create.content.decoration.encasing.EncasedBlock;
import com.simibubi.create.content.kinetics.simpleRelays.CogWheelBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static top.fmutren.crh.interaction.util.PredicatesCreator.isShaftCasing;

@Mixin(CogWheelBlock.class)
public class CreateCogWheelBlockMixin {

    @Inject(
            method = "setPlacedBy",
            at = @At("TAIL")
    )
    private void crh$EncaseAfterPlace(
            Level worldIn,
            BlockPos pos,
            BlockState state,
            LivingEntity placer,
            ItemStack stack,
            CallbackInfo ci)
    {
        if (!(placer instanceof Player player)) return;

        ItemStack heldOffHandItem = player.getOffhandItem();

        HitResult hit = Minecraft.getInstance().hitResult;
        if (hit.getType() != HitResult.Type.BLOCK) return;
        BlockHitResult blockHit = (BlockHitResult) hit;

        if(isShaftCasing(heldOffHandItem) && state.getBlock() instanceof EncasableBlock encasableBlock)
        {
            encasableBlock.tryEncase(
                    state,
                    worldIn,
                    pos,
                    heldOffHandItem,
                    player,
                    InteractionHand.MAIN_HAND,
                    blockHit);
        }
    }
}