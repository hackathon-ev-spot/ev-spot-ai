package org.rdutta.notificationservice.model;

import lombok.ToString;

@ToString
public class Notification {
    private final Object object;

    private Notification(Builder builder){
       this.object = builder.object;
    }
    public Object getObject() {
        return object;
    }
    public static class Builder{
        private Object object;

        public Builder(){}

        public Builder(Object object) {
            this.object = object;
        }

        public Builder object(Object object) {
            this.object = object;
            return this;
        }

        public Notification build(){
            return new Notification(this);
        }
    }
}
