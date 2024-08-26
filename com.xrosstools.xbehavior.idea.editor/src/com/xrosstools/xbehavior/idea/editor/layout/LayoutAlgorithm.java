package com.xrosstools.xbehavior.idea.editor.layout;

import com.xrosstools.xbehavior.idea.editor.model.BehaviorNode;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorNodeConnection;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorTreeDiagram;

import java.awt.*;
import java.util.List;

public class LayoutAlgorithm {
	private int charWidth = 10;
	private int margin = 100;
	private int horizantalSpace;
	private int verticalSpace;
	private int nodeHeight;
	private float alignment;

	boolean useNew = true;

	public void layout(BehaviorTreeDiagram diagram) {
		horizantalSpace = diagram.isHorizantal() ? diagram.getVerticalSpace() : diagram.getHorizantalSpace();
		verticalSpace = diagram.isHorizantal() ? diagram.getHorizantalSpace() : diagram.getVerticalSpace();

		nodeHeight = diagram.getNodeHeight();

		alignment = diagram.getAlignment();

		if(diagram.isHorizantal()) {
			int nextPos = margin; //+condition height
			for (BehaviorNode node : diagram.getRoots()) {
				nextPos += layoutHorizanta(margin, nextPos, node, 0) + verticalSpace;
			}
		} else {
			int branchWidth = 0;
			int nextLeftPos = margin;
			for (BehaviorNode node : diagram.getRoots()) {
				branchWidth += layoutVertical(nextLeftPos + branchWidth, node, 0) + horizantalSpace;
			}
		}
	}

	private int layoutHorizanta(int leftPosX, int leftPosY, BehaviorNode node, int depth) {
		int conditionWidth = getConnWidth(node.getInputs());
		int nodeWidth = getNodeWidth(node);
		int condiNodeWidth = Math.max(conditionWidth, nodeWidth);

		int childPosX = leftPosX + condiNodeWidth + horizantalSpace;
		int branchHeight = 0;
		for (BehaviorNodeConnection path : node.getOutputs()) {
			branchHeight += layoutHorizanta(childPosX, leftPosY + branchHeight, path.getTarget(), depth + 1) + verticalSpace;
		}
		branchHeight = branchHeight == 0 ? nodeHeight : branchHeight - verticalSpace;

		int x = leftPosX;
		int y = leftPosY + (int)((branchHeight - nodeHeight)* alignment);

		node.setLocation(new Point(x, y));
		//Make sure connection get refreshed
//		if(node.getInputs().get(0) != null)
//			node.getInput().layout();

		return branchHeight;
	}

	private int layoutVertical(int leftPos, BehaviorNode node, int depth) {
		int conditionWidth = getConnWidth(node.getInputs());
		int nodeWidth = getNodeWidth(node);
		int condiNodeWidth = Math.max(conditionWidth, nodeWidth);

		int branchWidth = 0;
		int nextLeftPos = leftPos;
		for (BehaviorNodeConnection path : node.getOutputs()) {
			branchWidth += layoutVertical(nextLeftPos + branchWidth, path.getTarget(), depth + 1) + horizantalSpace;
		}
		branchWidth = branchWidth == 0 ? 0 : branchWidth - horizantalSpace;

		int x = leftPos + locateLower(condiNodeWidth, nodeWidth);
		int y = margin + (depth) * (verticalSpace + nodeHeight);

		/**
		 *   conditionconditionconditioncondition
		 *              branchbranch
		 */
		if(condiNodeWidth >= branchWidth) {
			relocateBranch(node, locateLower(condiNodeWidth, branchWidth));
		} else {
			x += locateLower(branchWidth, condiNodeWidth);
		}

		node.setLocation(new Point(x, y));
		//Make sure connection get refreshed
//		if(node.getInputs().size() != 0)
//			node.getInputs().get(0).layout();

		return Math.max(branchWidth, condiNodeWidth);
	}

	private int getNodeWidth(BehaviorNode node) {
		return node.getSize().width;
	}

	private int getConnWidth(List<BehaviorNodeConnection> conns) {
		return 0;
	}

	/**
	 *   upperWidthupperWidthupperWidth
	 *             lowerWidth
	 */
	private int locateLower(int upperWidth, int lowerWidth) {
		return upperWidth >= lowerWidth ? (int)((upperWidth -lowerWidth) * alignment) : 0;
	}

	private void relocateBranch(BehaviorNode node, int delta) {
		for (BehaviorNodeConnection path : node.getOutputs()) {
			BehaviorNode child = path.getTarget();
			Point loc = child.getLocation();
			loc.x += delta;
			child.setLocation(loc);
			relocateBranch(child, delta);
		}
	}
}