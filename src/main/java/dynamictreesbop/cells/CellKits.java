package dynamictreesbop.cells;

import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.cells.CellNull;
import com.ferreusveritas.dynamictrees.api.cells.ICell;
import com.ferreusveritas.dynamictrees.api.cells.ICellKit;
import com.ferreusveritas.dynamictrees.api.cells.ICellSolver;
import com.ferreusveritas.dynamictrees.cells.CellNormal;
import com.ferreusveritas.dynamictrees.util.SimpleVoxmap;

import dynamictreesbop.DynamicTreesBOP;
import net.minecraft.util.ResourceLocation;

public class CellKits {
	
	public static void init() {
		new CellKits();
	}
	
	public CellKits() {
		TreeRegistry.registerCellKit(new ResourceLocation(DynamicTreesBOP.MODID, "bare"), bare);
		TreeRegistry.registerCellKit(new ResourceLocation(DynamicTreesBOP.MODID, "sparse"), sparse);
	}
	
	private final ICellKit bare = new ICellKit() {
		
		private final ICellSolver solver = new ICellSolver() {
			public int solve(ICell[] cells) {
				return 0;
			}
		};
		
		@Override
		public ICell getCellForLeaves(int hydro) {
			return CellNull.nullCell;
		}
		
		@Override
		public ICell getCellForBranch(int radius) {
			return CellNull.nullCell;
		}
		
		@Override
		public SimpleVoxmap getLeafCluster() {
			return DTBOPLeafClusters.bare;
		}
		
		@Override
		public ICellSolver getCellSolver() {
			return solver;
		}
		
		@Override
		public int getDefaultHydration() {
			return 0;
		}
		
	};
	
	private final ICellKit sparse = new ICellKit() {
		
		private final ICell sparseBranch = new CellSparseBranch();
		private final ICell sparseLeaves = new CellNormal(1);
		
		private final ICellSolver solver = new com.ferreusveritas.dynamictrees.cells.CellKits.BasicSolver(new short[] {0x0211});
		
		@Override
		public ICell getCellForLeaves(int hydro) {
			return hydro > 0 ? sparseLeaves : CellNull.nullCell;
		}
		
		@Override
		public ICell getCellForBranch(int radius) {
			return radius == 1 ? sparseBranch : CellNull.nullCell;
		}
		
		@Override
		public SimpleVoxmap getLeafCluster() {
			return DTBOPLeafClusters.sparse;
		}
		
		@Override
		public ICellSolver getCellSolver() {
			return solver;
		}
		
		@Override
		public int getDefaultHydration() {
			return 1;
		}
		
	};
	
}