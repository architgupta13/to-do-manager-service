package org.archit.todomanagerservice.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationUtil {

    public static final int MAX_PAGE_SIZE = 50;
    public static final String CREATED_DATE = "createdDate";
    private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.DESC, CREATED_DATE);

    public static Pageable withLimitAndDerivedSort(final int pageNumber, final int pageSize) {
        return PageRequest.of(pageNumber, deriveLimit(pageSize), DEFAULT_SORT);
    }


    private static int deriveLimit(final int limit) {
        return Math.min(limit, MAX_PAGE_SIZE);
    }

}
