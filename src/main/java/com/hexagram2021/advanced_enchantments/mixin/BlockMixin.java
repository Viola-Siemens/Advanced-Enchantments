package com.hexagram2021.advanced_enchantments.mixin;

import com.hexagram2021.advanced_enchantments.common.config.AECommonConfig;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PlayerHeadItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

@Mixin(Block.class)
public class BlockMixin {
	@SuppressWarnings("deprecation")
	@Inject(method = "getDrops(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)Ljava/util/List;", at = @At(value = "HEAD"), cancellable = true)
	private static void getDropsIfUsingSilkTouchII(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, @Nullable BlockEntity blockEntity, @Nullable Entity entity, ItemStack itemStack, CallbackInfoReturnable<List<ItemStack>> cir) {
		if(itemStack.getEnchantmentLevel(Enchantments.SILK_TOUCH) > 1) {
			ItemStack ret = blockState.getBlock().getCloneItemStack(serverLevel, blockPos, blockState);
			if(ret.isEmpty()) {
				cir.setReturnValue(Collections.emptyList());
				cir.cancel();
			} else {
				if(blockEntity != null && AECommonConfig.SILK_TOUCH_WITH_NBT.get()) {
					addCustomNbtData(ret, blockEntity);
				}
				cir.setReturnValue(ObjectArrayList.of(ret));
				cir.cancel();
			}
		}
	}

	private static void addCustomNbtData(ItemStack itemStack, BlockEntity blockEntity) {
		CompoundTag fullMetadata = blockEntity.saveWithFullMetadata();
		BlockItem.setBlockEntityData(itemStack, blockEntity.getType(), fullMetadata);
		if (itemStack.getItem() instanceof PlayerHeadItem && fullMetadata.contains("SkullOwner")) {
			CompoundTag compoundtag3 = fullMetadata.getCompound("SkullOwner");
			CompoundTag compoundtag4 = itemStack.getOrCreateTag();
			compoundtag4.put("SkullOwner", compoundtag3);
			CompoundTag compoundtag2 = compoundtag4.getCompound("BlockEntityTag");
			compoundtag2.remove("SkullOwner");
			compoundtag2.remove("x");
			compoundtag2.remove("y");
			compoundtag2.remove("z");
		} else {
			CompoundTag display = new CompoundTag();
			ListTag listtag = new ListTag();
			listtag.add(StringTag.valueOf("\"(+NBT)\""));
			display.put("Lore", listtag);
			itemStack.addTagElement("display", display);
		}
	}
}
