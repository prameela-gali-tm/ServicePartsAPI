package com.toyota.scs.serviceparts.util;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Sort;

public class SCSUtil {

	public SCSUtil() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static Predicate toPredicate(String search,Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder){
		List<SearchCriteria> list=searchHelper(search,root.getJavaType());
		
		List<Predicate> predicates = new ArrayList<>();
		for (SearchCriteria criteria : list) {
	        if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
	            predicates.add(builder.greaterThan(
	                    root.get(criteria.getKey()), criteria.getValue().toString()));
	        } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
	            predicates.add(builder.lessThan(
	                    root.get(criteria.getKey()), criteria.getValue().toString()));
	        } else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
	            predicates.add(builder.greaterThanOrEqualTo(
	                    root.get(criteria.getKey()), criteria.getValue().toString()));
	        } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
	            predicates.add(builder.lessThanOrEqualTo(
	                    root.get(criteria.getKey()), criteria.getValue().toString()));
	        } else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
	            predicates.add(builder.notEqual(
	                    root.get(criteria.getKey()), criteria.getValue()));
	        } else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
	            predicates.add(builder.equal(
	                    root.get(criteria.getKey()), criteria.getValue()));
	        } else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
	            predicates.add(builder.like(
	                    builder.lower(root.get(criteria.getKey())),
	                    "%" + criteria.getValue().toString().toLowerCase() + "%"));
	        } else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
	            predicates.add(builder.like(
	                    builder.lower(root.get(criteria.getKey())),
	                    criteria.getValue().toString().toLowerCase() + "%"));
	        } else if (criteria.getOperation().equals(SearchOperation.MATCH_START)) {
	            predicates.add(builder.like(
	                    builder.lower(root.get(criteria.getKey())),
	                    "%" + criteria.getValue().toString().toLowerCase()));
	        } else if (criteria.getOperation().equals(SearchOperation.IN)) {
	            predicates.add(builder.in(root.get(criteria.getKey())).value(criteria.getValue()));
	        } else if (criteria.getOperation().equals(SearchOperation.NOT_IN)) {
	            predicates.add(builder.not(root.get(criteria.getKey())).in(criteria.getValue()));
	        }
	    }
		if(!predicates.isEmpty()) {
			return builder.and(predicates.toArray(new Predicate[0]));
		}
		return null;
	}
	public static Sort sortHelper(String sort) {
		Sort sortBy=null;
		if(sort!=null&&!sort.isEmpty()) {
			Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
	        Matcher matcher = pattern.matcher(sort + ",");
	        while (matcher.find()) {
	        	Sort sortByTemp=Sort.by(matcher.group(1));
	        	if(matcher.group(3)=="desc") {
	        		sortByTemp=	sortByTemp.descending();
	        	}else {
	        		sortByTemp=	sortByTemp.ascending();
	        	}
	        	if(sortBy==null) {
	        		sortBy=sortByTemp;
	        	}else {
	        		sortBy.and(sortByTemp);
	        	}
	        }
		}
		return sortBy;
	}
	public static List<SearchCriteria> searchHelper(String search, Class<?> class1){
		Map<String, String> fieldMap =getfieldNameType(class1);
		List<SearchCriteria> searchList=new ArrayList<SearchCriteria> ();
		Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        String opr,field,fieldType;
        SearchOperation searchOpr;
        while (matcher.find()) {
        	SearchCriteria sc=new SearchCriteria();
        	field=matcher.group(1);
        	sc.setKey(field);
        	fieldType=fieldMap.get(field);
        	opr=matcher.group(2);
        	switch (opr) {
			case ":":
				searchOpr=SearchOperation.EQUAL;
				break;
			case "<":
				searchOpr=SearchOperation.LESS_THAN;
				break;
			case ">":
				searchOpr=SearchOperation.GREATER_THAN;
				break;
			case "<:":
				searchOpr=SearchOperation.LESS_THAN_EQUAL;
				break;
			case ">:":
				searchOpr=SearchOperation.GREATER_THAN_EQUAL;
				break;
			default:
				searchOpr=SearchOperation.EQUAL;
				break;
			}
        	sc.setOperation(searchOpr);        	
        	
        	
        	sc.setValue(getWrappedObject(fieldType, matcher.group(3)));
        	searchList.add(sc);
        }
		
		return searchList;
	}
	public static Map<String, String> getfieldNameType(Class c){
	    Map<String, String> nameType = new HashMap<String, String>();
	    Field[] fields = c.getDeclaredFields();
        
        for(Field f : fields){
            nameType.put(f.getName(), f.getType().getSimpleName());
            
        }
	    return nameType;
	}
public static Object getWrappedObject(String fieldType, String value)  {
		
		if( fieldType.equals(Date.class.getSimpleName())){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			try {
				return formatter.parse(value);
			} catch (ParseException e) {
				return null;
			}
		}
		else if( fieldType.equals("short") || fieldType.equals(Short.class.getSimpleName())){
			return Short.parseShort(value);
		}
		else if( fieldType.equals("int") || fieldType.equals(Integer.class.getSimpleName())){
			return Integer.parseInt(value);
		}
		else if( fieldType.equals("long") || fieldType.equals(Long.class.getSimpleName())){
			return Long.parseLong(value);
		}
		else if( fieldType.equals("float") || fieldType.equals(Float.class.getSimpleName())){
			return Float.parseFloat(value);
		}
		else if( fieldType.equals("double") || fieldType.equals(Double.class.getSimpleName())){
			return Double.parseDouble(value);
		}
		else if( fieldType.equals("boolean") || fieldType.equals(Boolean.class.getSimpleName())){
			return Boolean.parseBoolean(value);
		}
		else if( fieldType.equals(String.class.getSimpleName())){
			return value;
		}
		return null;
	}
}
