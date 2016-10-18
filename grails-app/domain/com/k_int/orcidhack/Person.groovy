package com.k_int.orcidhack

class Person {

  String name
  String persId
  String orcid
  String email

  static constraints = {
    orcid nullable:true, blank:false
    email nullable:true, blank:false
  }
}
