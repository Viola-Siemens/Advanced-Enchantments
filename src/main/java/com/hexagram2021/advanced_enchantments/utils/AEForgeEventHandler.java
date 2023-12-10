package com.hexagram2021.advanced_enchantments.utils;

import com.hexagram2021.advanced_enchantments.config.AEConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/**
 * @Project Advanced-Enchantments
 * @Author Hileb
 * @Date 2023/12/9 17:30
 **/
public class AEForgeEventHandler {
    /**
     * equal to:
     * @Inject(method = "getDrops(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)Ljava/util/List;", at = @At(value = "HEAD"), cancellable = true)
     * 	private static void getDropsIfUsingSilkTouchII(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, @Nullable BlockEntity blockEntity, @Nullable Entity entity, ItemStack itemStack, CallbackInfoReturnable<List<ItemStack>> cir) {
     * 		if(itemStack.getEnchantmentLevel(Enchantments.SILK_TOUCH) > 1) {
     * 			ItemStack ret = blockState.getBlock().getCloneItemStack(serverLevel, blockPos, blockState);
     * 			if(ret.isEmpty()) {
     * 				cir.setReturnValue(Collections.emptyList());
     * 				cir.cancel();
     *                        } else {
     * 				if(blockEntity != null && AECommonConfig.SILK_TOUCH_WITH_NBT.get()) {
     * 					addCustomNbtData(ret, blockEntity);
     *                }
     * 				cir.setReturnValue(ObjectArrayList.of(ret));
     * 				cir.cancel();
     *            }        * 		}
     *    }
     * **/
    @SubscribeEvent//ASM event Handler----To remove the normal drops
    public static void onBlockDrops(BlockEvent.HarvestDropsEvent event){
        if (AEConfig.enchantments.SILK_TOUCH_WITH_NBT){// the fast is judging a boolean before.
            EntityPlayer player=event.getHarvester();
            if (player!=null && !player.world.isRemote){
                if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH,player.getHeldItemMainhand())>1){
                    //minecraft use main-hand item to conclude the level of SILK_TOUCH
                    ItemStack stack=ItemStack.EMPTY;
                    IBlockState state = event.getState();

                    //get BlockItem
                    Item itemBlock=Item.getItemFromBlock(state.getBlock());
                    if (itemBlock != Items.AIR){
                        stack=new ItemStack(itemBlock);
                        if (itemBlock.getHasSubtypes())
                        {
                            stack.setItemDamage(state.getBlock().getMetaFromState(state));
                        }
                    }else {
                        for(ItemStack stack1:event.getDrops()){// find the block could be.
                            if (stack1.getItem() instanceof ItemBlock){
                                stack=stack1;
                                break;
                            }
                        }
                    }
                    if (stack.isEmpty()){
                        Item itemB= ForgeRegistries.ITEMS.getValue(state.getBlock().getRegistryName());
                        if (itemB!=null && itemB!=Items.AIR){
                            if (itemB.getHasSubtypes())
                            {
                                stack=new ItemStack(itemB, 1, state.getBlock().getMetaFromState(state));
                            }
                        }
                    }



                    TileEntity tileEntity=event.getWorld().getTileEntity(event.getPos());
                    if (tileEntity!=null){
                        AELogger.bigWarning("???");
                        if (!stack.hasTagCompound())stack.setTagCompound(new NBTTagCompound());
                        NBTTagCompound compound=stack.getTagCompound();
                        compound.setTag("BlockEntityTag", tileEntity.serializeNBT());
                        if (Items.SKULL==stack.getItem() && tileEntity instanceof TileEntitySkull){//especially in skull
                            NBTTagCompound p_1808129_t = new NBTTagCompound();
                            NBTUtil.writeGameProfile(p_1808129_t, ((TileEntitySkull)tileEntity).getPlayerProfile());
                            compound.setTag("SkullOwner", p_1808129_t);
                        }else {
                            NBTTagCompound display=new NBTTagCompound();
                            NBTTagList list = new NBTTagList();
                            list.appendTag(new NBTTagString("\"(+NBT)\""));
                            display.setTag("Lore", list);
                            compound.setTag("display",display);
                        }
                    }
                    event.getDrops().clear();//clear the old drops
                    event.getDrops().add(stack);//add correct drop
                    for(ItemStack stack1:event.getDrops()){//debug
                        AELogger.LOGGER.error(stack1.toString());
                    }
                }
            }
        }
    }
}
