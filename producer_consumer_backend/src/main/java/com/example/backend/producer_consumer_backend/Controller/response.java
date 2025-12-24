package com.example.backend.producer_consumer_backend.Controller;

public class response {
        private String content;

        public response(){

        }
        public response(String c){
            content = c;
        }
        public String getName() {
            return content;
        }

        public void setName(String name) {
            this.content = name;
        }
}
