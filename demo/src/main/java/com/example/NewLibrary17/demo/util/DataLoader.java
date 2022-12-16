package com.example.NewLibrary17.demo.util;

import com.example.NewLibrary17.demo.model.Client;
import com.example.NewLibrary17.demo.model.Role;
import com.example.NewLibrary17.demo.model.RoleName;
import com.example.NewLibrary17.demo.repository.ClientRepository;
import com.example.NewLibrary17.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {

            List<Role> roleList = new ArrayList<Role>();

            roleList = roleRepository.findAll();


            if(roleList.size()<2) {

                Role role1 = new Role(1, RoleName.USER, "Clients rol", new Timestamp(System.currentTimeMillis()), null);
                roleRepository.save(role1);

                Role role2 = new Role(2,RoleName.ADMIN, "Admins rol", new Timestamp( System.currentTimeMillis() ), null );
                roleRepository.save(role2);

            }

            if(clientRepository.findByEmail("admin")!=null)
                return;

            Role adminRole = roleRepository.findById(2).orElse(new Role(2,RoleName.ADMIN, "Admins rol", new Timestamp( System.currentTimeMillis() ), null ));

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String passEncoded = passwordEncoder.encode("admin");

            Client admin = new Client();
            admin.setName("admin");
            admin.setLastName("admin");
            admin.setEmail("admin");
            admin.setPassword(passEncoded);
            admin.setRole(adminRole);
            admin.setCreationDate(new Timestamp(System.currentTimeMillis()));
            admin.setSoftDelete(false);
            admin.setPhoneNumber("3464-573825");

            clientRepository.save(admin);
        }
        catch (Exception e){
            System.out.println(e);
        }

    }
}
