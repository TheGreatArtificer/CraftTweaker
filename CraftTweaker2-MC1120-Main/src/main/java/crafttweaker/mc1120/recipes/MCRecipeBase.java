package crafttweaker.mc1120.recipes;

import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.recipes.*;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;

import javax.annotation.Nullable;

public abstract class MCRecipeBase implements IRecipe, ICraftingRecipe {
    
    
    protected final ItemStack outputStack;
    protected final IItemStack output;
    protected final IRecipeFunction recipeFunction;
    protected final IRecipeAction recipeAction;
    protected final boolean hidden;
    protected NonNullList<Ingredient> ingredientList;
    protected ResourceLocation recipeNameLocation = new ResourceLocation("crafttweaker", "unInitializedRecipeName");
    
    MCRecipeBase(IItemStack output, NonNullList<Ingredient> ingredientList, IRecipeFunction recipeFunction, IRecipeAction recipeAction, boolean hidden) {
        this.output = output;
        this.outputStack = CraftTweakerMC.getItemStack(output);
        this.ingredientList = ingredientList;
        this.recipeFunction = recipeFunction;
        this.recipeAction = recipeAction;
        this.hidden = hidden;
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        return outputStack.copy();
    }
    
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredientList;
    }
    
    @Override
    public boolean isDynamic() {
        return hidden;
    }
    
    @Override
    public String getGroup() {
        return "";
    }
    
    @Override
    public IRecipe setRegistryName(ResourceLocation name) {
        this.recipeNameLocation = name;
        return this;
    }
    
    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return recipeNameLocation;
    }
    
    @Override
    public Class<IRecipe> getRegistryType() {
        return IRecipe.class;
    }
    
    public IRecipeAction getRecipeAction() {
        return recipeAction;
    }
    
    @Override
    public boolean hasRecipeAction() {
        return recipeAction != null;
    }
    
    @Override
    public boolean hasRecipeFunction() {
        return recipeFunction != null;
    }
    
    @Override
    public boolean isHidden() {
        return hidden;
    }
    
    @Override
    public String getFullResourceName() {
        return String.valueOf(getRegistryName());
    }
    
    @Override
    public String getResourceDomain() {
        return getRegistryName() == null ? "null" : getRegistryName().getResourceDomain();
    }
    
    @Override
    public String getName() {
        return getRegistryName() != null ? getRegistryName().getResourcePath() : "null";
    }
    
    @Override
    public IItemStack getOutput() {
        return output;
    }
    
    public boolean isVisible() {
        return !isDynamic();
    }
    
    @Override
    public void applyTransformers(ICraftingInventory inventory, IPlayer byPlayer) {
        if(inventory.getInternal() instanceof InventoryCrafting) {
            applyTransformers((InventoryCrafting) inventory.getInternal(), byPlayer);
        }
    }
    
    public abstract void applyTransformers(InventoryCrafting inventory, IPlayer byPlayer);
    
    @Override
    public boolean matches(ICraftingInventory inventory) {
        return inventory.getInternal() instanceof InventoryCrafting && matches((InventoryCrafting) inventory.getInternal(), inventory.getPlayer() == null ? null : CraftTweakerMC.getWorld(inventory.getPlayer().getWorld()));
    }
    
    @Override
    public IItemStack getCraftingResult(ICraftingInventory inventory) {
        if(inventory.getInternal() instanceof InventoryCrafting)
            return CraftTweakerMC.getIItemStack(getCraftingResult((InventoryCrafting) inventory));
        return null;
    }
    
    public abstract MCRecipeBase update();
}
