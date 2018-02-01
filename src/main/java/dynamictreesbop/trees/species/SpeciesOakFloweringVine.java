package dynamictreesbop.trees.species;

import java.util.List;

import com.ferreusveritas.dynamictrees.ModConfigs;
import com.ferreusveritas.dynamictrees.ModConstants;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.blocks.BlockRootyDirt;
import com.ferreusveritas.dynamictrees.items.Seed;
import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreatorApple;
import com.ferreusveritas.dynamictrees.trees.DynamicTree;
import com.ferreusveritas.dynamictrees.trees.Species;

import biomesoplenty.api.biome.BOPBiomes;
import biomesoplenty.api.block.BOPBlocks;
import dynamictreesbop.DynamicTreesBOP;
import dynamictreesbop.featuregen.FeatureGenVine;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class SpeciesOakFloweringVine extends Species {
	
	Species baseSpecies;
	FeatureGenVine vineGen;
	
	public SpeciesOakFloweringVine(DynamicTree treeFamily) {
		super(new ResourceLocation(DynamicTreesBOP.MODID, "oakfloweringvine"), treeFamily);
		
		baseSpecies = TreeRegistry.findSpecies(new ResourceLocation(ModConstants.MODID, "oak"));
		
		setBasicGrowingParameters(0.3f, 12.0f, upProbability, lowestBranchHeight, 0.8f);
		
		envFactor(Type.COLD, 0.75f);
		envFactor(Type.HOT, 0.75f);
		envFactor(Type.DRY, 0.625f);
		envFactor(Type.FOREST, 1.05f);
		
		if(ModConfigs.worldGen && !ModConfigs.enableAppleTrees) {//If we've disabled apple trees we still need some way to get apples.
			addDropCreator(new DropCreatorApple());
		}
		
		setupStandardSeedDropping();
		
		vineGen = new FeatureGenVine(this).setQuantity(12).setMaxLength(6).setRayDistance(8).setVineBlock(BOPBlocks.flower_vine);
	}
	
	@Override
	public boolean isBiomePerfect(Biome biome) {
		return isOneOfBiomes(biome, BOPBiomes.mystic_grove.get());
	}
	
	@Override
	public boolean isAcceptableSoil(World world, BlockPos pos, IBlockState soilBlockState) {
		Block soilBlock = soilBlockState.getBlock();
		return soilBlock == Blocks.DIRT || soilBlock == Blocks.GRASS || soilBlock == Blocks.MYCELIUM || soilBlock instanceof BlockRootyDirt || soilBlock == BOPBlocks.grass || soilBlock == BOPBlocks.dirt;
	}
	
	@Override
	public ItemStack getSeedStack(int qty) {
		return baseSpecies.getSeedStack(qty);
	}
	
	@Override
	public Seed getSeed() {
		return baseSpecies.getSeed();
	}
	
	@Override
	public void postGeneration(World world, BlockPos rootPos, Biome biome, int radius, List<BlockPos> endPoints, boolean worldGen) {
		super.postGeneration(world, rootPos, biome, radius, endPoints, worldGen);
		
		//Generate Vines
		vineGen.gen(world, rootPos.up(), endPoints);
	}
	
}