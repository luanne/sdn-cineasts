package org.neo4j.cineasts.converter;


import org.neo4j.cineasts.domain.User;
import org.neo4j.ogm.typeconversion.AttributeConverter;

import java.util.ArrayList;
import java.util.List;

public class UserRolesConverter implements AttributeConverter<User.Roles[],String[]> {


    @Override
    public String[] toGraphProperty(User.Roles[] value) {
        if(value==null) {
            return null;
        }
        String[] values = new String[(value.length)];
        int i=0;
        for(User.Roles role : value) {
            values[i++]=role.name();
        }
        return values;
    }

    @Override
    public User.Roles[] toEntityAttribute(String[] value) {
        User.Roles[] roles =new User.Roles[value.length];
        int i=0;
        for(String role : value) {
            roles[i++] = User.Roles.valueOf(role);
        }
        return roles;
    }
}
