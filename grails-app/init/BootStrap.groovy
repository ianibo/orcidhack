package com.k_int.orcidhack

class BootStrap {

    def init = { servletContext ->

      def user_role = Role.findByAuthority('ROLE_USER') ?: new Role(authority: 'ROLE_USER').save(failOnError: true)

      log.debug("Create admin user...");
      def adminUser = User.findByUsername('admin')
      if ( ! adminUser ) {
        adminUser = new User(
            username: 'admin',
            password: 'admin',
            display: 'Admin',
            email: 'admin@localhost',
            enabled: true).save(failOnError: true)
      }

      // Make sure admin user has all the system roles.
      [user_role].each { role ->
        if (!adminUser.authorities.contains(role)) {
          UserRole.create adminUser, role
        }
      }

      def shu_repo = Repository.findByName('shu')
      if ( shu_repo == null ) {
        shu_repo = new Repository(name:'shu',baseUrl:'http://shura.shu.ac.uk/cgi/oai2').save(flush:true, failOnError:true);
      }

      def org_shu = Org.findByName('shu')
      if ( org_shu == null ) {
        org_shu = new Org(name:'shu').save(flush:true, failOnError:true)
      }

      def person_acesii = Person.findByPersId('acesii')
      if ( person_acesii == null ) {
        person_acesii = new Person(persId:'acesii', name:'IBBOTSON, Ian').save(flush:true, failOnError:true)
        def pao = new PersonOrgAffiliation(person:person_acesii, org:org_shu).save(flush:true, failOnError:true);
      }

      def item = Item.findByOaiId('oai:shura.shu.ac.uk:12191')
      if ( item == null ) {
        item = new Item(
                        oaiId:'oai:shura.shu.ac.uk:12191', 
                        title:'Nifty with data: can a business intelligence analysis sourced from open data form a nifty assignment?').save(flush:true, failOnError:true)

        def nameOccurrence = new NameOccurrence(
                                            item:item, 
                                            person:person_acesii, 
                                            name:'IBBOTSON, Ian').save(flush:true, failOnError:true)
      }


    }
    def destroy = {
    }
}
