package org.nextime.ion.backoffice.tree;

import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionServlet;
import org.nextime.ion.framework.business.*;
import org.nextime.ion.framework.mapping.*;

public class WcmTreeBuilder implements TreeBuilder {

	/**
	 * @see org.nextime.ion.backoffice.tree.TreeBuilder#buildTree(TreeControl, ActionServlet, HttpServletRequest)
	 */
	public void buildTree(
		TreeControl treeControl,
		ActionServlet servlet,
		HttpServletRequest request) {

		TreeControlNode root = treeControl.getRoot();

		try {
			Mapping.begin();
			Vector roots = Section.listRootSections();
			for (int i = 0; i < roots.size(); i++) {
				Section section = (Section) roots.get(i);
				TreeControlNode node = buildNode(section);
				//if( i==0 ) treeControl.selectNode(section.getId());
				root.addChild(node);
				recursive(section, node);
			}
			Mapping.rollback();
		} catch (Exception e) {
			Mapping.rollback();
			e.printStackTrace();
		}
	}

	public static TreeControlNode buildNode(Section section) {
		String img = "section.gif";
		if ("offline".equals(section.getMetaData("status"))) {
			img = "section-offline.gif";
		}
		TreeControlNode node =
			new TreeControlNode(
				section.getId(),
				img,
				(section.getMetaData("name") != null)
					? section.getMetaData("name") + ""
					: "<i>(indéfinie)</i>",
				"viewSection.x?id=" + section.getId(),
				"content",
				false);
		return node;
	}

	private void recursive(Section section, TreeControlNode node) {
		Vector childs = section.listSubSections();
		for (int i = 0; i < childs.size(); i++) {
			Section tsection = (Section) childs.get(i);
			TreeControlNode tnode = buildNode(tsection);
			node.addChild(tnode);
			recursive(tsection, tnode);
		}
	}

}
