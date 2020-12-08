package algorithm;

public class MinSubTree {
	private int minSum;
	private TreeNode minRoot;
	
	/**
	 * @param root: the root of binary tree
	 * @return the root of the minimum subtree
	 */
	public TreeNode findSubtree(TreeNode root)
	{
		minSum = Integer.MAX_VALUE;
		minRoot = null;
		getSum(root);
		return minRoot;
	}
	
	private int getSum(TreeNode root)
	{
		if(root == null)
		{
			return 0;
		}
		
		int sum = getSum(root.left) + getSum(root.right) + root.val;
		if(sum < minSum)
		{
			minSum = sum;
			minRoot = root;
		}
		
		return sum;
	}
}
