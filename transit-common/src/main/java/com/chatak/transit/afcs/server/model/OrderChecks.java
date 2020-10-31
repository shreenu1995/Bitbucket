package com.chatak.transit.afcs.server.model;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence(value= {NotEmptyAndNull.class,SizeCheck.class,RegexCheck.class,Default.class})
public interface OrderChecks {

}
