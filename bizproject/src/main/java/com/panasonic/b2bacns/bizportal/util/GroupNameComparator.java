/**
 * 
 */
package com.panasonic.b2bacns.bizportal.util;

import java.io.Serializable;
import java.util.Comparator;

import com.panasonic.b2bacns.bizportal.group.vo.GroupLeftMenuVO;

import thirdparty.com.daveKoelle.sort.AlphanumComparator;

/**
 * @author simanchal.patra
 *
 */
public class GroupNameComparator implements Comparator<GroupLeftMenuVO>,
		Serializable {

	private static final long serialVersionUID = -1835544663024664231L;

	private AlphanumComparator<String> groupNameComparator = new AlphanumComparator<>();

	@Override
	public int compare(GroupLeftMenuVO group1, GroupLeftMenuVO group2) {
		return groupNameComparator.compare(group1.getGroupName(),
				group2.getGroupName());
	}
}
