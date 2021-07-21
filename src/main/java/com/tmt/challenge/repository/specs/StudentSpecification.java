package com.tmt.challenge.repository.specs;

import com.tmt.challenge.constant.enums.Operator;
import com.tmt.challenge.constant.enums.SearchOperation;
import com.tmt.challenge.model.Student;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentSpecification implements Specification<Student> {

    private List<SearchCriteria> list;

    private Operator operator;

    public StudentSpecification() {

        this.list = new ArrayList<>();
        this.operator = Operator.AND;
    }

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }

    public void operator(Operator operator) {
        this.operator = operator;
    }

    @Override
    public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        for (SearchCriteria criteria : list) {
            if (criteria.getOperation().equals(SearchOperation.DATE_GREATER_THAN_EQUAL)) {
                if (criteria.getJoinColumnName() != null && !criteria.getJoinColumnName().isEmpty()) {
                    Join<Object, Object> join = root.join(criteria.getJoinColumnName());
                    predicates.add(builder.greaterThanOrEqualTo(join.<Date>get(criteria.getKey()), (Date)criteria.getValue()));
                } else {
                    predicates.add(builder.greaterThanOrEqualTo(root.<Date>get(criteria.getKey()), (Date)criteria.getValue()));
                }
            } else if (criteria.getOperation().equals(SearchOperation.DATE_LESS_THAN_EQUAL)) {
                if (criteria.getJoinColumnName() != null && !criteria.getJoinColumnName().isEmpty()) {
                    Join<Object, Object> join = root.join(criteria.getJoinColumnName());
                    predicates.add(builder.lessThanOrEqualTo(join.<Date>get(criteria.getKey()), (Date)criteria.getValue()));
                } else {
                    predicates.add(builder.lessThanOrEqualTo(root.<Date>get(criteria.getKey()), (Date)criteria.getValue()));
                }
            } else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                if (criteria.getJoinColumnName() != null && !criteria.getJoinColumnName().isEmpty()) {
                    Join<Object, Object> join = root.join(criteria.getJoinColumnName());
                    predicates.add(builder.equal(join.get(criteria.getKey()), criteria.getValue()));
                } else {
                    predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
                }
            } else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
                if (criteria.getJoinColumnName() != null && !criteria.getJoinColumnName().isEmpty()) {
                    Join<Object, Object> join = root.join(criteria.getJoinColumnName());
                    predicates.add(builder.like(builder.lower(join.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%"));
                } else {
                    predicates.add(builder.like(builder.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%"));
                }
            } else if (criteria.getOperation().equals(SearchOperation.MATCH_START)) {
                if (criteria.getJoinColumnName() != null && !criteria.getJoinColumnName().isEmpty()) {
                    Join<Object, Object> join = root.join(criteria.getJoinColumnName());
                    predicates.add(builder.like(builder.lower(join.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase()));
                } else {
                    predicates.add(builder.like(builder.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase()));
                }
            } else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
                if (criteria.getJoinColumnName() != null && !criteria.getJoinColumnName().isEmpty()) {
                    Join<Object, Object> join = root.join(criteria.getJoinColumnName());
                    predicates.add(builder.like(builder.lower(join.get(criteria.getKey())), criteria.getValue().toString().toLowerCase() + "%"));
                } else {
                    predicates.add(builder.like(builder.lower(root.get(criteria.getKey())), criteria.getValue().toString().toLowerCase() + "%"));
                }
            } else if (criteria.getOperation().equals(SearchOperation.DATE_BETWEEN)) {
                if (criteria.getJoinColumnName() != null && !criteria.getJoinColumnName().isEmpty()) {
                    Join<Object, Object> join = root.join(criteria.getJoinColumnName());
                    predicates.add(builder.between(join.<Date>get(criteria.getKey()), (Date)criteria.getStartDate(), (Date)criteria.getEndDate()));
                } else {
                    predicates.add(builder.between(root.<Date>get(criteria.getKey()), (Date)criteria.getStartDate(), (Date)criteria.getEndDate()));
                }
            } else if (criteria.getOperation().equals(SearchOperation.IN)) {
                if (criteria.getJoinColumnName() != null && !criteria.getJoinColumnName().isEmpty()) {
                    Join<Object, Object> join = root.join(criteria.getJoinColumnName());
                    predicates.add(builder.in(join.get(criteria.getKey())).value(criteria.getValue()));
                } else {
                    predicates.add(builder.in(root.get(criteria.getKey())).value(criteria.getValue()));
                }
            } else if (criteria.getOperation().equals(SearchOperation.NOT_IN)) {
                if (criteria.getJoinColumnName() != null && !criteria.getJoinColumnName().isEmpty()) {
                    Join<Object, Object> join = root.join(criteria.getJoinColumnName());
                    predicates.add(builder.in(join.get(criteria.getKey())).value(criteria.getValue()).not());
                } else {
                    predicates.add(builder.in(root.get(criteria.getKey())).value(criteria.getValue()).not());
                }
            } else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
                if (criteria.getJoinColumnName() != null && !criteria.getJoinColumnName().isEmpty()) {
                    Join<Object, Object> join = root.join(criteria.getJoinColumnName());
                    predicates.add(builder.greaterThan(join.get(criteria.getKey()), (String)criteria.getValue()));
                } else {
                    predicates.add(builder.greaterThan(root.get(criteria.getKey()), (String)criteria.getValue()));
                }
            } else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
                if (criteria.getJoinColumnName() != null && !criteria.getJoinColumnName().isEmpty()) {
                    Join<Object, Object> join = root.join(criteria.getJoinColumnName());
                    predicates.add(builder.greaterThanOrEqualTo(join.get(criteria.getKey()), (String)criteria.getValue()));
                } else {
                    predicates.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()), (String)criteria.getValue()));
                }
            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
                if (criteria.getJoinColumnName() != null && !criteria.getJoinColumnName().isEmpty()) {
                    Join<Object, Object> join = root.join(criteria.getJoinColumnName());
                    predicates.add(builder.lessThan(join.get(criteria.getKey()), (String)criteria.getValue()));
                } else {
                    predicates.add(builder.lessThan(root.get(criteria.getKey()), (String)criteria.getValue()));
                }
            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
                if (criteria.getJoinColumnName() != null && !criteria.getJoinColumnName().isEmpty()) {
                    Join<Object, Object> join = root.join(criteria.getJoinColumnName());
                    predicates.add(builder.lessThanOrEqualTo(join.get(criteria.getKey()), (String)criteria.getValue()));
                } else {
                    predicates.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()), (String)criteria.getValue()));
                }
            } else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
                if (criteria.getJoinColumnName() != null && !criteria.getJoinColumnName().isEmpty()) {
                    Join<Object, Object> join = root.join(criteria.getJoinColumnName());
                    predicates.add(builder.notEqual(join.get(criteria.getKey()), criteria.getValue()));
                } else {
                    predicates.add(builder.notEqual(root.get(criteria.getKey()), criteria.getValue()));
                }
            }
        }
        if (operator.equals(Operator.OR)) return builder.or(predicates.toArray(new Predicate[0]));
        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
