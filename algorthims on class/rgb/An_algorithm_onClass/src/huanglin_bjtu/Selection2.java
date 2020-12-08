package algorithm;

import java.util.*;

//version2: divide & conquer
public class Selection2 {
	public ArrayList<Integer> preorderTraversal(TreeNode root)
	{
		ArrayList<Integer> result = new ArrayList<Integer>();
		// null or leaf
		if(root == null) 
		{
			return result;
		}
		
		// divide
		ArrayList<Integer> left = preorderTraversal(root.left);
		ArrayList<Integer> right = preorderTraversal(root.right);
		
		//conquer
		result.add(root.val);
		result.addAll(left);
		result.addAll(right);
		return result;
	}
}
