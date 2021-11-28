module sultn.springboot {
  requires com.fasterxml.jackson.databind;

  requires spring.web;
  requires spring.beans;
  requires spring.boot;
  requires spring.context;
  requires spring.boot.autoconfigure;

  requires sultn.core;
  requires sultn.json;

  opens sultn.springboot to spring.beans, spring.context, spring.web;
}
