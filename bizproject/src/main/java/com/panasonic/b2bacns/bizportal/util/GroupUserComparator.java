package com.panasonic.b2bacns.bizportal.util;

import java.io.Serializable;

import com.panasonic.b2bacns.bizportal.usermanagement.vo.GroupUserVO;

import thirdparty.com.daveKoelle.sort.AlphanumComparator;

public class GroupUserComparator extends AlphanumComparator<GroupUserVO>
		implements Serializable {

	private static final long serialVersionUID = -5021190855420054514L;

	private AlphanumComparator<String> groupUserComparator = new AlphanumComparator<>();

	@Override
	public int compare(GroupUserVO group1, GroupUserVO group2) {
		return groupUserComparator.compare(group1.getGroupName(),
				group2.getGroupName());
	}
}
