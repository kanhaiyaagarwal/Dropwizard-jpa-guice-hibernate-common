package com.boomerang.workflowconnector.internal.enums;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

/**
 * Created by kanhaiya on 23/09/16.
 */
public enum Status {


    CREATED, RUNNING, COMPLETED, FAILED;

    private static final Multimap<Status, Status> STATUS_CHANGES = ArrayListMultimap.create();

    static {
        STATUS_CHANGES.putAll(CREATED, Lists.newArrayList(RUNNING, FAILED));
        STATUS_CHANGES.putAll(RUNNING, Lists.newArrayList(COMPLETED,FAILED));
    }

    public boolean canChangeTo(Status toStatus) {
        return STATUS_CHANGES.containsEntry(this, toStatus);
    }
}