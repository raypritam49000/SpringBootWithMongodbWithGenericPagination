package com.mongo.search.builder;

import com.mongo.search.pageable.PageableClass;
import org.springframework.data.domain.Page;

import java.util.ArrayList;


public class PageableUtilities {

    public static <T,
            PageDTO extends PageableClass<T>>
    PageDTO transferToPageDTO(
            Page<T> entityPage,
            PageDTO pageDTO,
            String verifiedSortColumn,
            String verifiedSortOrder,
            String pluralResourceName) {

        pageDTO.setHasContent(entityPage.hasContent());
        pageDTO.setHasNext(entityPage.hasNext());
        pageDTO.setHasPrevious(entityPage.hasPrevious());
        pageDTO.setFirst(entityPage.isFirst());
        pageDTO.setLast(entityPage.isLast());

        pageDTO.setTotalElements(entityPage.getTotalElements());

        pageDTO.setTotalPages(entityPage.getTotalPages());

        pageDTO.setData(entityPage.getContent());

        pageDTO.setPerPage(entityPage.getSize());
        pageDTO.setPageNumber(entityPage.getNumber());
        pageDTO.setSize(entityPage.getSize());

        pageDTO.setPluralResourceName(pluralResourceName);

        if (verifiedSortOrder != null && !verifiedSortOrder.equals("")
                && verifiedSortColumn != null && !verifiedSortColumn.equals("")) {
            pageDTO.setSortOrder(verifiedSortOrder);
            pageDTO.setSortColumn(verifiedSortColumn);
            pageDTO.setSorted(true);
        }

        return pageDTO;
    }

}
