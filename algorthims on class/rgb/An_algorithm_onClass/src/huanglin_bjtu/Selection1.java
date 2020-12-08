package algorithm;

import java.util.*;

//version 1: traverse
public class Selection1 {
	public ArrayList<Integer> preorderTraversal(TreeNode root)
	{
		ArrayList<Integer> result = new ArrayList<Integer>();
		traverse(root, result);
		return result;
	}
	
	//把root为根的preorder加入result里面
	private void traverse(TreeNode root, ArrayList<Integer> result)
	{
		if(root == null)
		{
			return;
		}
		
		result.add(root.val);
		traverse(root.left, result);
		traverse(root.right, result);
	}
}


