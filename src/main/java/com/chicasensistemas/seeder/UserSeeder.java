package com.chicasensistemas.seeder;

import com.chicasensistemas.auth.RoleType;
import com.chicasensistemas.model.entity.RoleEntity;
import com.chicasensistemas.model.entity.UserEntity;
import com.chicasensistemas.repository.IRoleRepository;
import com.chicasensistemas.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class UserSeeder implements CommandLineRunner {

  private static final List<String> NAMES_ADMIN = List.of("Luitgard", "Christel", "Stefanie",
      "Oswald", "Ottomar", "Johann", "Moses", "Ianis", "Ashlyn", "Maria");
  private static final List<String> LAST_NAMES_ADMIN = List.of("Dettler", "Kampmann", "Wesener",
      "Wagner-Kesler", "Redel", "Macht", "Galloway", "Ianis", "Blanchette", "Pauling");
  private static final List<String> EMAILS_ADMIN = List.of("admin@admin.com",
      "epchristel15@gmail.com", "jfstefanie5@gmail.com", "gkelsenbach10@gmail.com",
      "jjottomar9@gmail.com", "erleitner17@gmail.com", "hamoses0@gmail.com", "behoyt4@gmail.com",
      "adblanchette3@gmail.com", "dkpauling10@gmail.com");
  private static final List<String> PASSWORDS_ADMIN = List.of("12345678", "p00846e4p", "f94475j9f",
      "k15564g6k", "j654110j9j", "r05400e4r", "a987103h7a", "e62685b1e", "d25548a0d", "k97165d3k");
  private static final List<String> NAMES_USER = List.of("Kunigunde", "Marlies",
      "Lieselotte", "Carina", "Gabriele", "Gertrud", "Cecilia", "Sonje", "Gitte", "Auguste");
  private static final List<String> LAST_NAMES_USER = List.of("Alderborn", "Holseiner", "Heberle",
      "Milanovich", "Andes", "Castro", "Baumler", "Sonje", "Wagner-Schell", "Wagner-Kees");
  private static final List<String> EMAILS_USER = List.of("user@user.com",
      "jpschell15@gmail.com", "airupel8@gmail.com", "cpcarina15@gmail.com",
      "hrgastelu17@gmail.com", "huweinmeier-r20@gmail.com", "esplatz18@gmail.com",
      "cysonje24@gmail.com", "gdgitte3@gmail.com", "clsarvott11@gmail.com");
  private static final List<String> PASSWORDS_USER = List.of("12345678", "p96776j9p", "i117107a0i",
      "p09919c2p", "r80744h7r", "u83385h7u", "s23664e4s",
      "y59736c2y", "d30762g6d", "l22929c2l");

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        seedUserTable();
    }

    private void seedUserTable() {
        if (userRepository.count() == 0) {
            createAdminUser();
            createStandardUser();
        }
    }

    private void createAdminUser() {
        List<RoleEntity> roleAdmin = Collections.singletonList(
                roleRepository.findByName(RoleType.ADMIN.getFullRoleName()));
        for (int i = 0; i < 10; i++) {
            createUser(
                    NAMES_ADMIN.get(i),
                    LAST_NAMES_ADMIN.get(i),
                    EMAILS_ADMIN.get(i),
                    PASSWORDS_ADMIN.get(i),
                    roleAdmin,
                    "ADMIN USER");
        }
    }

    private void createStandardUser() {
        List<RoleEntity> roleUser = Collections.singletonList(
                roleRepository.findByName(RoleType.USER.getFullRoleName()));
        for (int i = 0; i < 10; i++) {
            createUser(
                    NAMES_USER.get(i),
                    LAST_NAMES_USER.get(i),
                    EMAILS_USER.get(i),
                    PASSWORDS_USER.get(i),
                    roleUser,
                    "STANDARD USER");
        }
    }

    private void createUser(String firstName, String lastName, String email, String password,
                            List<RoleEntity> role, String description) {
        UserEntity user = new UserEntity();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setPhone("261-foo-1234");
        user.setPhoto("");
        user.setDescription(description);
        user.setSoftDelete(false);
        user.setRoles(role);
        userRepository.save(user);
    }
}
