package hr.algebra.fruity.runner;

import hr.algebra.fruity.model.Agent;
import hr.algebra.fruity.model.AgentState;
import hr.algebra.fruity.model.ArcodeParcel;
import hr.algebra.fruity.model.Attachment;
import hr.algebra.fruity.model.CadastralMunicipality;
import hr.algebra.fruity.model.CadastralParcel;
import hr.algebra.fruity.model.CadastralParcelOwnershipStatus;
import hr.algebra.fruity.model.County;
import hr.algebra.fruity.model.Employee;
import hr.algebra.fruity.model.EmployeeRole;
import hr.algebra.fruity.model.Equipment;
import hr.algebra.fruity.model.FruitCultivar;
import hr.algebra.fruity.model.FruitSpecies;
import hr.algebra.fruity.model.HarvestedFruitClass;
import hr.algebra.fruity.model.MobileToken;
import hr.algebra.fruity.model.Realisation;
import hr.algebra.fruity.model.RealisationAgent;
import hr.algebra.fruity.model.RealisationAttachment;
import hr.algebra.fruity.model.RealisationEquipment;
import hr.algebra.fruity.model.RealisationHarvest;
import hr.algebra.fruity.model.RealisationRow;
import hr.algebra.fruity.model.RefreshToken;
import hr.algebra.fruity.model.RegistrationToken;
import hr.algebra.fruity.model.Row;
import hr.algebra.fruity.model.RowCluster;
import hr.algebra.fruity.model.UnitOfMeasure;
import hr.algebra.fruity.model.User;
import hr.algebra.fruity.model.Work;
import hr.algebra.fruity.model.WorkAgent;
import hr.algebra.fruity.model.WorkAttachment;
import hr.algebra.fruity.model.WorkEmployee;
import hr.algebra.fruity.model.WorkEquipment;
import hr.algebra.fruity.model.WorkRow;
import hr.algebra.fruity.model.WorkType;
import hr.algebra.fruity.model.codebook.AgentStates;
import hr.algebra.fruity.model.codebook.CadastralParcelOwnershipStatuses;
import hr.algebra.fruity.model.codebook.EmployeeRoles;
import hr.algebra.fruity.model.codebook.HarvestedFruitClasses;
import hr.algebra.fruity.model.codebook.WorkTypes;
import hr.algebra.fruity.repository.AgentRepository;
import hr.algebra.fruity.repository.AgentStateRepository;
import hr.algebra.fruity.repository.ArcodeParcelRepository;
import hr.algebra.fruity.repository.AttachmentRepository;
import hr.algebra.fruity.repository.CadastralMunicipalityRepository;
import hr.algebra.fruity.repository.CadastralParcelOwnershipStatusRepository;
import hr.algebra.fruity.repository.CadastralParcelRepository;
import hr.algebra.fruity.repository.CountyRepository;
import hr.algebra.fruity.repository.EmployeeRepository;
import hr.algebra.fruity.repository.EmployeeRoleRepository;
import hr.algebra.fruity.repository.EquipmentRepository;
import hr.algebra.fruity.repository.FruitCultivarRepository;
import hr.algebra.fruity.repository.FruitSpeciesRepository;
import hr.algebra.fruity.repository.HarvestedFruitClassRepository;
import hr.algebra.fruity.repository.MobileTokenRepository;
import hr.algebra.fruity.repository.RealisationAgentRepository;
import hr.algebra.fruity.repository.RealisationAttachmentRepository;
import hr.algebra.fruity.repository.RealisationEquipmentRepository;
import hr.algebra.fruity.repository.RealisationHarvestRepository;
import hr.algebra.fruity.repository.RealisationRepository;
import hr.algebra.fruity.repository.RealisationRowRepository;
import hr.algebra.fruity.repository.RefreshTokenRepository;
import hr.algebra.fruity.repository.RegistrationTokenRepository;
import hr.algebra.fruity.repository.RowClusterRepository;
import hr.algebra.fruity.repository.RowRepository;
import hr.algebra.fruity.repository.UnitOfMeasureRepository;
import hr.algebra.fruity.repository.UserRepository;
import hr.algebra.fruity.repository.WorkAgentRepository;
import hr.algebra.fruity.repository.WorkAttachmentRepository;
import hr.algebra.fruity.repository.WorkEmployeeRepository;
import hr.algebra.fruity.repository.WorkEquipmentRepository;
import hr.algebra.fruity.repository.WorkRepository;
import hr.algebra.fruity.repository.WorkRowRepository;
import hr.algebra.fruity.repository.WorkTypeRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class BootstrapDbRunner implements CommandLineRunner {

  private final PasswordEncoder passwordEncoder;

  private final UserRepository userRepository;

  private final CountyRepository countyRepository;

  private final CadastralMunicipalityRepository cadastralMunicipalityRepository;

  private final CadastralParcelRepository cadastralParcelRepository;

  private final ArcodeParcelRepository arcodeParcelRepository;

  private final RowClusterRepository rowClusterRepository;

  private final FruitSpeciesRepository fruitSpeciesRepository;

  private final FruitCultivarRepository fruitCultivarRepository;

  private final RowRepository rowRepository;

  private final EmployeeRoleRepository employeeRoleRepository;

  private final RefreshTokenRepository refreshTokenRepository;

  private final RegistrationTokenRepository registrationTokenRepository;

  private final MobileTokenRepository mobileTokenRepository;

  private final EmployeeRepository employeeRepository;

  private final EquipmentRepository equipmentRepository;

  private final AttachmentRepository attachmentRepository;

  private final WorkTypeRepository workTypeRepository;

  private final WorkRepository workRepository;

  private final WorkEmployeeRepository workEmployeeRepository;

  private final WorkRowRepository workRowRepository;

  private final WorkEquipmentRepository workEquipmentRepository;

  private final WorkAttachmentRepository workAttachmentRepository;

  private final CadastralParcelOwnershipStatusRepository cadastralParcelOwnershipStatusRepository;

  private final AgentStateRepository agentStateRepository;

  private final AgentRepository agentRepository;

  private final UnitOfMeasureRepository unitOfMeasureRepository;

  private final WorkAgentRepository workAgentRepository;

  private final RealisationRepository realisationRepository;

  private final RealisationRowRepository realisationRowRepository;

  private final RealisationEquipmentRepository realisationEquipmentRepository;

  private final RealisationAttachmentRepository realisationAttachmentRepository;

  private final RealisationAgentRepository realisationAgentRepository;

  private final HarvestedFruitClassRepository harvestedFruitClassRepository;

  private final RealisationHarvestRepository realisationHarvestRepository;

  @Override
  public void run(String... args) {

    // BEGIN INSERT COUNTIES
    var county1 = County.builder().abbreviation("BBŽ").name("Bjelovarsko-bilogorska županija").build();
    var county2 = County.builder().abbreviation("BPŽ").name("Brodsko-posavska županija").build();
    var county3 = County.builder().abbreviation("DNŽ").name("Dubrovačko-neretvanska županija").build();
    var county4 = County.builder().abbreviation("IŽ").name("Istarska županija").build();
    var county5 = County.builder().abbreviation("KŽ").name("Karlovačka županija").build();
    var county6 = County.builder().abbreviation("KKŽ").name("Koprivničko-križevačka županija").build();
    var county7 = County.builder().abbreviation("KZŽ").name("Krapinsko-zagorska županija").build();
    var county8 = County.builder().abbreviation("LSŽ").name("Ličko-senjska županija").build();
    var county9 = County.builder().abbreviation("MŽ").name("Međimurska županija").build();
    var county10 = County.builder().abbreviation("OBŽ").name("Osječko-baranjska županija").build();
    var county11 = County.builder().abbreviation("PSŽ").name("Požeško-slavonska županija").build();
    var county12 = County.builder().abbreviation("PGŽ").name("Primorsko-goranska županija").build();
    var county13 = County.builder().abbreviation("SMŽ").name("Sisačko-moslavačka županija").build();
    var county14 = County.builder().abbreviation("SDŽ").name("Splitsko-dalmatinska županija").build();
    var county15 = County.builder().abbreviation("ŠKŽ").name("Šibensko-kninska županija").build();
    var county16 = County.builder().abbreviation("VŽ").name("Varaždinska županija").build();
    var county17 = County.builder().abbreviation("VPŽ").name("Virovitičko-podravska županija").build();
    var county18 = County.builder().abbreviation("VSŽ").name("Vukovarsko-srijemska županija").build();
    var county19 = County.builder().abbreviation("ZDŽ").name("Zadarska županija").build();
    var county20 = County.builder().abbreviation("ZGŽ").name("Zagrebačka županija").build();
    var county21 = County.builder().abbreviation("GZG").name("Grad Zagreb").build();

    countyRepository.saveAll(
      List.of(
        county1, county2, county3, county4, county5, county6, county7, county8, county9, county10,
        county11, county12, county13, county14, county15, county16, county17, county18, county19, county20,
        county21
      )
    );
    // END INSERT COUNTIES

    // BEGIN INSERT USERS
    val user1 = User.builder()
      .name("SOPG Bratko Gordana")
      .address("Brezje 5, 40311 Lopatinec")
      .oib("73915770084")
      .phoneNumber("091/154-70-45")
      .county(county9)
      .build();

    userRepository.saveAll(
      List.of(
        user1
      )
    );
    // END INSERT COUNTIES

    // BEGIN INSERT CADASTRAL_MUNICIPALITIES
    val cadastralMunicipality1 = CadastralMunicipality.builder().registrationNumber(314684).name("Nakovanj").department("Korčula").regionalCadastreOffice("PUK Dubrovnik").build();
    val cadastralMunicipality2 = CadastralMunicipality.builder().registrationNumber(330914).name("Nard").department("Valpovo").regionalCadastreOffice("PUK Osijek").build();
    val cadastralMunicipality3 = CadastralMunicipality.builder().registrationNumber(317390).name("Našice").department("Našice").regionalCadastreOffice("PUK Osijek").build();
    val cadastralMunicipality4 = CadastralMunicipality.builder().registrationNumber(331228).name("Natkrižovljan").department("Varaždin").regionalCadastreOffice("PUK Varaždin").build();
    val cadastralMunicipality5 = CadastralMunicipality.builder().registrationNumber(305162).name("Nebljusi").department("Donji Lapac").regionalCadastreOffice("PUK Gospić").build();
    val cadastralMunicipality6 = CadastralMunicipality.builder().registrationNumber(331236).name("Nedeljanec").department("Varaždin").regionalCadastreOffice("PUK Varaždin").build();
    val cadastralMunicipality7 = CadastralMunicipality.builder().registrationNumber(334197).name("Negoslavci").department("Vukovar").regionalCadastreOffice("PUK Vukovar").build();
    val cadastralMunicipality8 = CadastralMunicipality.builder().registrationNumber(329665).name("Neorić").department("Solin").regionalCadastreOffice("PUK Split").build();
    val cadastralMunicipality9 = CadastralMunicipality.builder().registrationNumber(302406).name("Nerezine").department("Mali Lošinj").regionalCadastreOffice("PUK Rijeka").build();
    val cadastralMunicipality10 = CadastralMunicipality.builder().registrationNumber(311391).name("Dapčevica").department("Grubišno Polje").regionalCadastreOffice("PUK Bjelovar").build();

    cadastralMunicipalityRepository.saveAll(
      List.of(
        cadastralMunicipality1, cadastralMunicipality2, cadastralMunicipality3, cadastralMunicipality4, cadastralMunicipality5, cadastralMunicipality6, cadastralMunicipality7, cadastralMunicipality8, cadastralMunicipality9, cadastralMunicipality10
      )
    );
    // END INSERT CADASTRAL_MUNICIPALITIES

    // BEGIN INSERT CADASTRAL_PARCEL_OWNERSHIP_STATUSES
    val cadastralParcelOwnershipStatus1 = CadastralParcelOwnershipStatus.builder().name(CadastralParcelOwnershipStatuses.OWNED.name()).displayName(CadastralParcelOwnershipStatuses.OWNED.displayName()).build();
    val cadastralParcelOwnershipStatus2 = CadastralParcelOwnershipStatus.builder().name(CadastralParcelOwnershipStatuses.LEASED.name()).displayName(CadastralParcelOwnershipStatuses.LEASED.displayName()).build();

    cadastralParcelOwnershipStatusRepository.saveAll(
      List.of(
        cadastralParcelOwnershipStatus1, cadastralParcelOwnershipStatus2
      )
    );
    // END INSERT CADASTRAL_PARCEL_OWNERSHIP_STATUSES

    // BEGIN INSERT CADASTRAL_PARCELS
    val cadastralParcel1 = CadastralParcel.builder()
      .user(user1)
      .name("Polje doma")
      .cadastralMunicipality(cadastralMunicipality6)
      .cadastralNumber("6566")
      .surface(BigDecimal.valueOf(0.7200))
      .ownershipStatus(cadastralParcelOwnershipStatus1)
      .build();

    cadastralParcelRepository.saveAll(
      List.of(
        cadastralParcel1
      )
    );
    // END INSERT CADASTRAL_PARCELS

    // BEGIN INSERT ARCODE_PARCELS
    val arcodeParcel1 = ArcodeParcel.builder().cadastralParcel(cadastralParcel1).arcode(2970284).name("Oranica doma").user(user1).surface(BigDecimal.valueOf(0.7200)).build();
    val arcodeParcel2 = ArcodeParcel.builder().cadastralParcel(cadastralParcel1).arcode(2970303).name("Oranica doma 2").user(user1).surface(BigDecimal.valueOf(0.1800)).build();

    arcodeParcelRepository.saveAll(
      List.of(
        arcodeParcel1, arcodeParcel2
      )
    );
    // END INSERT ARCODE_PARCELS

    // BEGIN INSERT ROW_CLUSTERS
    val rowCluster1 = RowCluster.builder().arcodeParcel(arcodeParcel1).user(user1).name("T1 Malina Polana").surface(BigDecimal.valueOf(715.00)).build();
    val rowCluster2 = RowCluster.builder().arcodeParcel(arcodeParcel1).user(user1).name("T2 Kupina Lochness").surface(BigDecimal.valueOf(887.50)).build();
    val rowCluster3 = RowCluster.builder().arcodeParcel(arcodeParcel1).user(user1).name("T3 Malina Sugana").surface(BigDecimal.valueOf(875.00)).build();
    val rowCluster4 = RowCluster.builder().arcodeParcel(arcodeParcel1).user(user1).name("T4 Aronija Nero").surface(BigDecimal.valueOf(720.00)).build();
    val rowCluster5 = RowCluster.builder().arcodeParcel(arcodeParcel1).user(user1).name("T5 Ribiz crni Titania").surface(BigDecimal.valueOf(1080.00)).build();
    val rowCluster6 = RowCluster.builder().arcodeParcel(arcodeParcel1).user(user1).name("T6 Ribiz crveni Rovada").surface(BigDecimal.valueOf(585.00)).build();
    val rowCluster7 = RowCluster.builder().arcodeParcel(arcodeParcel1).user(user1).name("T7 Ribiz crni Titania").surface(BigDecimal.valueOf(585.00)).build();
    val rowCluster8 = RowCluster.builder().arcodeParcel(arcodeParcel1).user(user1).name("T8 Aronia Nero").surface(BigDecimal.valueOf(408.75)).build();
    val rowCluster9 = RowCluster.builder().arcodeParcel(arcodeParcel1).user(user1).name("T9 Aronia Viking").surface(BigDecimal.valueOf(221.25)).build();

    rowClusterRepository.saveAll(
      List.of(
        rowCluster1, rowCluster2, rowCluster3, rowCluster4, rowCluster5, rowCluster6, rowCluster7, rowCluster8, rowCluster9
      )
    );
    // END INSERT ROW_CLUSTERS

    // BEGIN INSERT FRUIT_SPECIES
    val fruitSpecies1 = FruitSpecies.builder().name("Malina").build();
    val fruitSpecies2 = FruitSpecies.builder().name("Kupina").build();
    val fruitSpecies3 = FruitSpecies.builder().name("Crni ribizl").build();
    val fruitSpecies4 = FruitSpecies.builder().name("Crveni ribizl").build();
    val fruitSpecies5 = FruitSpecies.builder().name("Aronija").build();
    val fruitSpecies6 = FruitSpecies.builder().name("Borovnica").build();

    fruitSpeciesRepository.saveAll(
      List.of(
        fruitSpecies1, fruitSpecies2, fruitSpecies3, fruitSpecies4, fruitSpecies5, fruitSpecies6
      )
    );
    // END INSERT FRUIT_SPECIES

    // BEGIN INSERT FRUIT_CULTIVARS
    val fruitCultivar1 = FruitCultivar.builder().fruitSpecies(fruitSpecies1).name("Polana").build();
    val fruitCultivar2 = FruitCultivar.builder().fruitSpecies(fruitSpecies1).name("Sugana").build();
    val fruitCultivar3 = FruitCultivar.builder().fruitSpecies(fruitSpecies2).name("Lochness").build();
    val fruitCultivar4 = FruitCultivar.builder().fruitSpecies(fruitSpecies3).name("Titania").build();
    val fruitCultivar5 = FruitCultivar.builder().fruitSpecies(fruitSpecies4).name("Rovada").build();
    val fruitCultivar6 = FruitCultivar.builder().fruitSpecies(fruitSpecies5).name("Nero").build();
    val fruitCultivar7 = FruitCultivar.builder().fruitSpecies(fruitSpecies5).name("Viking").build();

    fruitCultivarRepository.saveAll(
      List.of(
        fruitCultivar1, fruitCultivar2, fruitCultivar3, fruitCultivar4, fruitCultivar5, fruitCultivar6, fruitCultivar7
      )
    );
    // END INSERT FRUIT_CULTIVARS

    // BEGIN INSERT ROWS
    val row1 = Row.builder().user(user1).rowCluster(rowCluster1).ordinal(1).plantingYear(2015).numberOfSeedlings(44).fruitCultivar(fruitCultivar1).build();
    val row2 = Row.builder().user(user1).rowCluster(rowCluster1).ordinal(2).plantingYear(2015).numberOfSeedlings(44).fruitCultivar(fruitCultivar1).build();
    val row3 = Row.builder().user(user1).rowCluster(rowCluster1).ordinal(3).plantingYear(2015).numberOfSeedlings(44).fruitCultivar(fruitCultivar1).build();
    val row4 = Row.builder().user(user1).rowCluster(rowCluster1).ordinal(4).plantingYear(2015).numberOfSeedlings(44).fruitCultivar(fruitCultivar1).build();
    val row5 = Row.builder().user(user1).rowCluster(rowCluster1).ordinal(5).plantingYear(2015).numberOfSeedlings(44).fruitCultivar(fruitCultivar1).build();
    val row6 = Row.builder().user(user1).rowCluster(rowCluster1).ordinal(6).plantingYear(2015).numberOfSeedlings(44).fruitCultivar(fruitCultivar1).build();
    val row7 = Row.builder().user(user1).rowCluster(rowCluster1).ordinal(7).plantingYear(2015).numberOfSeedlings(44).fruitCultivar(fruitCultivar1).build();
    val row8 = Row.builder().user(user1).rowCluster(rowCluster1).ordinal(8).plantingYear(2015).numberOfSeedlings(44).fruitCultivar(fruitCultivar1).build();
    val row9 = Row.builder().user(user1).rowCluster(rowCluster1).ordinal(9).plantingYear(2015).numberOfSeedlings(44).fruitCultivar(fruitCultivar1).build();
    val row10 = Row.builder().user(user1).rowCluster(rowCluster1).ordinal(10).plantingYear(2015).numberOfSeedlings(44).fruitCultivar(fruitCultivar1).build();
    val row11 = Row.builder().user(user1).rowCluster(rowCluster1).ordinal(11).plantingYear(2015).numberOfSeedlings(44).fruitCultivar(fruitCultivar1).build();
    val row12 = Row.builder().user(user1).rowCluster(rowCluster1).ordinal(12).plantingYear(2015).numberOfSeedlings(44).fruitCultivar(fruitCultivar1).build();
    val row13 = Row.builder().user(user1).rowCluster(rowCluster1).ordinal(13).plantingYear(2015).numberOfSeedlings(44).fruitCultivar(fruitCultivar1).build();

    val row14 = Row.builder().user(user1).rowCluster(rowCluster2).ordinal(1).plantingYear(2015).numberOfSeedlings(44).fruitCultivar(fruitCultivar3).build();
    val row15 = Row.builder().user(user1).rowCluster(rowCluster2).ordinal(2).plantingYear(2015).numberOfSeedlings(44).fruitCultivar(fruitCultivar3).build();
    val row16 = Row.builder().user(user1).rowCluster(rowCluster2).ordinal(3).plantingYear(2015).numberOfSeedlings(44).fruitCultivar(fruitCultivar3).build();
    val row17 = Row.builder().user(user1).rowCluster(rowCluster2).ordinal(4).plantingYear(2015).numberOfSeedlings(44).fruitCultivar(fruitCultivar3).build();
    val row18 = Row.builder().user(user1).rowCluster(rowCluster2).ordinal(5).plantingYear(2015).numberOfSeedlings(44).fruitCultivar(fruitCultivar3).build();
    val row19 = Row.builder().user(user1).rowCluster(rowCluster2).ordinal(6).plantingYear(2015).numberOfSeedlings(44).fruitCultivar(fruitCultivar3).build();
    val row20 = Row.builder().user(user1).rowCluster(rowCluster2).ordinal(7).plantingYear(2015).numberOfSeedlings(44).fruitCultivar(fruitCultivar3).build();
    val row21 = Row.builder().user(user1).rowCluster(rowCluster2).ordinal(8).plantingYear(2015).numberOfSeedlings(44).fruitCultivar(fruitCultivar3).build();
    val row22 = Row.builder().user(user1).rowCluster(rowCluster2).ordinal(9).plantingYear(2015).numberOfSeedlings(44).fruitCultivar(fruitCultivar3).build();
    val row23 = Row.builder().user(user1).rowCluster(rowCluster2).ordinal(10).plantingYear(2015).numberOfSeedlings(44).fruitCultivar(fruitCultivar3).build();

    rowRepository.saveAll(
      List.of(
        row1, row2, row3, row4, row5, row6, row7, row8, row9, row10, row11, row12, row13,
        row14, row15, row16, row17, row18, row19, row20, row21, row22, row23
      )
    );
    // END INSERT ROWS

    // BEGIN INSERT EMPLOYEE_ROLES
    val employeeRole1 = EmployeeRole.builder()
      .name(EmployeeRoles.ROLE_MANAGER.name())
      .displayName(EmployeeRoles.ROLE_MANAGER.displayName())
      .build();

    val employeeRole2 = EmployeeRole.builder()
      .name(EmployeeRoles.ROLE_PERFORMER.name())
      .displayName(EmployeeRoles.ROLE_PERFORMER.displayName())
      .build();

    employeeRoleRepository.saveAll(
      List.of(
        employeeRole1, employeeRole2
      )
    );
    // END INSERT EMPLOYEE_ROLES

    // BEGIN INSERT REFRESH_TOKENS
    val refreshToken1 = RefreshToken.builder().expireDateTime(LocalDateTime.now().plusDays(1)).build();
    val refreshToken2 = RefreshToken.builder().expireDateTime(LocalDateTime.now().plusDays(1)).build();
    val refreshToken3 = RefreshToken.builder().expireDateTime(LocalDateTime.now().plusDays(1)).build();
    val refreshToken4 = RefreshToken.builder().expireDateTime(LocalDateTime.now().plusDays(1)).build();
    val refreshToken5 = RefreshToken.builder().expireDateTime(LocalDateTime.now().plusDays(1)).build();

    refreshTokenRepository.saveAll(
      List.of(
        refreshToken1, refreshToken2, refreshToken3, refreshToken4, refreshToken5
      )
    );
    // END INSERT REFRESH_TOKENS

    // BEGIN INSERT REGISTRATION_TOKENS
    val registrationToken1 = RegistrationToken.builder().createDateTime(LocalDateTime.now().minusMinutes(15)).expireDateTime(LocalDateTime.now()).confirmDateTime(LocalDateTime.now().minusMinutes(10)).build();

    registrationTokenRepository.saveAll(
      List.of(
        registrationToken1
      )
    );
    // END INSERT REGISTRATION_TOKENS

    // BEGIN INSERT MOBILE_TOKENS
    val mobileToken1 = MobileToken.builder().build();
    val mobileToken2 = MobileToken.builder().build();
    val mobileToken3 = MobileToken.builder().build();
    val mobileToken4 = MobileToken.builder().build();
    val mobileToken5 = MobileToken.builder().build();

    mobileTokenRepository.saveAll(
      List.of(
        mobileToken1, mobileToken2, mobileToken3, mobileToken4, mobileToken5
      )
    );
    // END INSERT MOBILE_TOKENS

    // BEGIN INSERT EMPLOYEES
    val employee1 = Employee.builder()
      .user(user1)
      .firstName("Gordana")
      .lastName("Bratko")
      .username("opgbratko")
      .email("gbratko@gmail.com")
      .phoneNumber("091/526-0832")
      .costPerHour(BigDecimal.valueOf(6.50))
      .password(passwordEncoder.encode("Pa$$w0rd"))
      .role(employeeRole1)
      .refreshToken(refreshToken1)
      .registrationToken(registrationToken1)
      .mobileToken(mobileToken1)
      .enabled(true)
      .locked(false)
      .build();
    val employee2 = Employee.builder().user(user1).firstName("Drago").lastName("Bratko").costPerHour(BigDecimal.valueOf(6.50)).role(employeeRole2).refreshToken(refreshToken2).mobileToken(mobileToken2).enabled(true).locked(false).build();
    val employee3 = Employee.builder().user(user1).firstName("Marija").lastName("Hajdinjak").costPerHour(BigDecimal.valueOf(4.25)).role(employeeRole2).refreshToken(refreshToken3).mobileToken(mobileToken3).enabled(true).locked(false).build();
    val employee4 = Employee.builder().user(user1).firstName("Dora").lastName("Bratko").costPerHour(BigDecimal.valueOf(4.00)).role(employeeRole2).refreshToken(refreshToken4).mobileToken(mobileToken4).enabled(true).locked(false).build();
    val employee5 = Employee.builder().user(user1).firstName("Karlo").lastName("Bratko").costPerHour(BigDecimal.valueOf(4.00)).role(employeeRole2).refreshToken(refreshToken5).mobileToken(mobileToken5).enabled(true).locked(false).build();

    employeeRepository.saveAll(
      List.of(
        employee1, employee2, employee3, employee4, employee5
      )
    );
    // END INSERT EMPLOYEES

    // BEGIN INSERT ATTACHMENTS
    val attachment1 = Attachment.builder()
      .user(user1)
      .name("Prikolica")
      .productionYear(2002)
      .costPerHour(BigDecimal.valueOf(0))
      .purchasePrice(BigDecimal.valueOf(500))
      .build();

    attachmentRepository.saveAll(
      List.of(
        attachment1
      )
    );
    // END INSERT ATTACHMENTS

    // BEGIN INSERT EQUIPMENTS
    val equipment1 = Equipment.builder()
      .user(user1)
      .name("Traktor")
      .productionYear(1991)
      .costPerHour(BigDecimal.valueOf(20.50))
      .purchasePrice(BigDecimal.valueOf(2200))
      .attachment(attachment1)
      .build();

    val equipment2 = Equipment.builder()
      .user(user1)
      .name("SOLO automizer")
      .productionYear(1998)
      .costPerHour(BigDecimal.valueOf(5.50))
      .purchasePrice(BigDecimal.valueOf(400))
      .build();

    equipmentRepository.saveAll(
      List.of(
        equipment1, equipment2
      )
    );
    // END INSERT EQUIPMENTS

    // BEGIN INSERT WORK_TYPES
    val workType1 = WorkType.builder().name(WorkTypes.MOWING.name()).displayName(WorkTypes.MOWING.displayName()).workersTab(WorkTypes.MOWING.workersTab()).rowsTab(WorkTypes.MOWING.rowsTab()).equipmentsTab(WorkTypes.MOWING.equipmentsTab()).attachmentsTab(WorkTypes.MOWING.attachmentsTab()).agentsTab(WorkTypes.MOWING.agentsTab()).quantitiesTab(WorkTypes.MOWING.agentsTab()).build();
    val workType2 = WorkType.builder().name(WorkTypes.PLANTING.name()).displayName(WorkTypes.PLANTING.displayName()).workersTab(WorkTypes.PLANTING.workersTab()).rowsTab(WorkTypes.PLANTING.rowsTab()).equipmentsTab(WorkTypes.PLANTING.equipmentsTab()).attachmentsTab(WorkTypes.PLANTING.attachmentsTab()).agentsTab(WorkTypes.PLANTING.agentsTab()).quantitiesTab(WorkTypes.PLANTING.agentsTab()).build();
    val workType3 = WorkType.builder().name(WorkTypes.PRUNING.name()).displayName(WorkTypes.PRUNING.displayName()).workersTab(WorkTypes.PRUNING.workersTab()).rowsTab(WorkTypes.PRUNING.rowsTab()).equipmentsTab(WorkTypes.PRUNING.equipmentsTab()).attachmentsTab(WorkTypes.PRUNING.attachmentsTab()).agentsTab(WorkTypes.PRUNING.agentsTab()).quantitiesTab(WorkTypes.PRUNING.agentsTab()).build();
    val workType4 = WorkType.builder().name(WorkTypes.IRRIGATION.name()).displayName(WorkTypes.IRRIGATION.displayName()).workersTab(WorkTypes.IRRIGATION.workersTab()).rowsTab(WorkTypes.IRRIGATION.rowsTab()).equipmentsTab(WorkTypes.IRRIGATION.equipmentsTab()).attachmentsTab(WorkTypes.IRRIGATION.attachmentsTab()).agentsTab(WorkTypes.IRRIGATION.agentsTab()).quantitiesTab(WorkTypes.IRRIGATION.agentsTab()).build();
    val workType5 = WorkType.builder().name(WorkTypes.DRESSING.name()).displayName(WorkTypes.DRESSING.displayName()).workersTab(WorkTypes.DRESSING.workersTab()).rowsTab(WorkTypes.DRESSING.rowsTab()).equipmentsTab(WorkTypes.DRESSING.equipmentsTab()).attachmentsTab(WorkTypes.DRESSING.attachmentsTab()).agentsTab(WorkTypes.DRESSING.agentsTab()).quantitiesTab(WorkTypes.DRESSING.agentsTab()).build();
    val workType6 = WorkType.builder().name(WorkTypes.PROTECTION.name()).displayName(WorkTypes.PROTECTION.displayName()).workersTab(WorkTypes.PROTECTION.workersTab()).rowsTab(WorkTypes.PROTECTION.rowsTab()).equipmentsTab(WorkTypes.PROTECTION.equipmentsTab()).attachmentsTab(WorkTypes.PROTECTION.attachmentsTab()).agentsTab(WorkTypes.PROTECTION.agentsTab()).quantitiesTab(WorkTypes.PROTECTION.agentsTab()).build();
    val workType7 = WorkType.builder().name(WorkTypes.HARVEST.name()).displayName(WorkTypes.HARVEST.displayName()).workersTab(WorkTypes.HARVEST.workersTab()).rowsTab(WorkTypes.HARVEST.rowsTab()).equipmentsTab(WorkTypes.HARVEST.equipmentsTab()).attachmentsTab(WorkTypes.HARVEST.attachmentsTab()).agentsTab(WorkTypes.HARVEST.agentsTab()).quantitiesTab(WorkTypes.HARVEST.agentsTab()).build();

    workTypeRepository.saveAll(
      List.of(
        workType1, workType2, workType3, workType4, workType5, workType6, workType7
      )
    );
    // END INSERT WORK_TYPES

    // BEGIN INSERT WORKS
    val work1 = Work.builder()
      .user(user1)
      .startDateTime(LocalDateTime.now().minusDays(10))
      .endDateTime(LocalDateTime.now().minusDays(9).plusHours(3))
      .finished(true)
      .note("Ne kombinirati Bijelo ulje i Sumpor.")
      .type(workType6)
      .build();

    val work2 = Work.builder()
      .user(user1)
      .startDateTime(LocalDateTime.now())
      .endDateTime(LocalDateTime.now().plusDays(1))
      .type(workType7)
      .build();

    workRepository.saveAll(
      List.of(
        work1, work2
      )
    );
    // END INSERT WORKS

    // BEGIN INSERT WORK_EMPLOYEES
    val workEmployee1 = WorkEmployee.builder()
      .work(work1)
      .employee(employee5)
      .costPerHour(employee5.getCostPerHour().add(BigDecimal.valueOf(2)))
      .note("Obavezno tretiraj s obje strane.")
      .build();

    val workEmployee2 = WorkEmployee.builder()
      .work(work2)
      .employee(employee1)
      .build();

    val workEmployee3 = WorkEmployee.builder()
      .work(work2)
      .employee(employee4)
      .build();

    workEmployeeRepository.saveAll(
      List.of(
        workEmployee1, workEmployee2, workEmployee3
      )
    );
    // END INSERT WORK_EMPLOYEES

    // BEGIN INSERT WORK_ROWS
    val workRow1 = WorkRow.builder().work(work1).row(row1).build();
    val workRow2 = WorkRow.builder().work(work1).row(row2).build();
    val workRow3 = WorkRow.builder().work(work1).row(row3).build();
    val workRow4 = WorkRow.builder().work(work1).row(row4).build();
    val workRow5 = WorkRow.builder().work(work1).row(row5).build();
    val workRow6 = WorkRow.builder().work(work1).row(row6).build();

    val workRow7 = WorkRow.builder().work(work2).row(row1).build();
    val workRow8 = WorkRow.builder().work(work2).row(row2).build();
    val workRow9 = WorkRow.builder().work(work2).row(row3).build();

    workRowRepository.saveAll(
      List.of(
        workRow1, workRow2, workRow3, workRow4, workRow5, workRow6, workRow7, workRow8, workRow9
      )
    );
    // END INSERT WORK_ROWS

    // BEGIN INSERT WORK_EQUIPMENTS
    val workEquipment1 = WorkEquipment.builder().work(work1).equipment(equipment2).note("Obavezno staviti 12 litara vode.").build();
    val workEquipment2 = WorkEquipment.builder().work(work2).equipment(equipment1).build();

    workEquipmentRepository.saveAll(
      List.of(
        workEquipment1, workEquipment2
      )
    );
    // END INSERT WORK_EQUIPMENTS

    // BEGIN INSERT WORK_ATTACHMENTS
    val workAttachment1 = WorkAttachment.builder().work(work2).attachment(attachment1).build();

    workAttachmentRepository.saveAll(
      List.of(
        workAttachment1
      )
    );
    // END INSERT WORK_ATTACHMENTS

    // BEGIN INSERT AGENT_STATES
    val agentState1 = AgentState.builder().name(AgentStates.HARD.name()).displayName(AgentStates.HARD.displayName()).build();
    val agentState2 = AgentState.builder().name(AgentStates.LIQUID.name()).displayName(AgentStates.LIQUID.displayName()).build();
    val agentState3 = AgentState.builder().name(AgentStates.GRANULAR.name()).displayName(AgentStates.GRANULAR.displayName()).build();
    val agentState4 = AgentState.builder().name(AgentStates.CRYSTAL_GRANULES.name()).displayName(AgentStates.CRYSTAL_GRANULES.displayName()).build();
    val agentState5 = AgentState.builder().name(AgentStates.UNKNOWN.name()).displayName(AgentStates.UNKNOWN.displayName()).build();

    agentStateRepository.saveAll(
      List.of(
        agentState1, agentState2, agentState3, agentState4, agentState5
      )
    );
    // END INSERT AGENT_STATES

    // BEGIN INSERT AGENTS
    val agent1 = Agent.builder().name("ALBATROS HYDRO").manufacturer("Yara").state(agentState1).build();

    agentRepository.saveAll(
      List.of(
        agent1
      )
    );
    // END INSERT AGENTS

    // BEGIN INSERT UNITS_OF_MEASURE
    val unitOfMeasure1 = UnitOfMeasure.builder().name("Litra").abbreviation("L").build();
    val unitOfMeasure2 = UnitOfMeasure.builder().name("Mililitra").abbreviation("mL").base(unitOfMeasure1).baseMultiplier(BigDecimal.valueOf(0.001)).build();
    val unitOfMeasure3 = UnitOfMeasure.builder().name("Centilitra").abbreviation("cL").base(unitOfMeasure1).baseMultiplier(BigDecimal.valueOf(0.01)).build();
    val unitOfMeasure4 = UnitOfMeasure.builder().name("Decilitra").abbreviation("dL").base(unitOfMeasure1).baseMultiplier(BigDecimal.valueOf(0.1)).build();
    val unitOfMeasure5 = UnitOfMeasure.builder().name("Hektolitra").abbreviation("hL").base(unitOfMeasure1).baseMultiplier(BigDecimal.valueOf(100)).build();
    val unitOfMeasure6 = UnitOfMeasure.builder().name("Gram").abbreviation("g").build();
    val unitOfMeasure7 = UnitOfMeasure.builder().name("Dekagram").abbreviation("dg").base(unitOfMeasure6).baseMultiplier(BigDecimal.valueOf(10)).build();
    val unitOfMeasure8 = UnitOfMeasure.builder().name("Kilogram").abbreviation("kg").base(unitOfMeasure6).baseMultiplier(BigDecimal.valueOf(1000)).build();

    unitOfMeasureRepository.saveAll(
      List.of(
        unitOfMeasure1, unitOfMeasure2, unitOfMeasure3, unitOfMeasure4, unitOfMeasure5, unitOfMeasure6, unitOfMeasure7, unitOfMeasure8
      )
    );
    // END INSERT UNITS_OF_MEASURE

    // BEGIN INSERT WORK_AGENTS
    val workAgent1 = WorkAgent.builder().work(work1).agent(agent1).agentQuantity(BigDecimal.valueOf(32)).agentUnitOfMeasure(unitOfMeasure6).waterQuantity(BigDecimal.valueOf(12)).waterUnitOfMeasure(unitOfMeasure1).costPerUnitOfMeasure(BigDecimal.valueOf(5.20)).build();

    workAgentRepository.saveAll(
      List.of(
        workAgent1
      )
    );
    // END INSERT WORK_AGENTS

    // BEGIN INSERT REALISATIONS
    val realisation1 = Realisation.builder().work(work1).employee(employee5).startDateTime(work1.getStartDateTime().plusHours(1)).endDateTime(work1.getStartDateTime().plusHours(3)).note("Sve odrađeno po dogovoru.").build();
    val realisation2 = Realisation.builder().work(work2).employee(employee1).startDateTime(work2.getStartDateTime().plusHours(1)).endDateTime(work2.getStartDateTime().plusHours(3)).build();

    realisationRepository.saveAll(
      List.of(
        realisation1, realisation2
      )
    );
    // END INSERT REALISATIONS

    // BEGIN INSERT REALISATION_ROWS
    val realisationRow1 = RealisationRow.builder().realisation(realisation1).row(row1).build();
    val realisationRow2 = RealisationRow.builder().realisation(realisation1).row(row2).build();
    val realisationRow3 = RealisationRow.builder().realisation(realisation1).row(row3).build();
    val realisationRow4 = RealisationRow.builder().realisation(realisation1).row(row4).build();
    val realisationRow5 = RealisationRow.builder().realisation(realisation1).row(row5).build();
    val realisationRow6 = RealisationRow.builder().realisation(realisation1).row(row6).build();

    val realisationRow7 = RealisationRow.builder().realisation(realisation1).row(row1).build();
    val realisationRow8 = RealisationRow.builder().realisation(realisation1).row(row2).build();
    val realisationRow9 = RealisationRow.builder().realisation(realisation1).row(row3).build();

    realisationRowRepository.saveAll(
      List.of(
        realisationRow1, realisationRow2, realisationRow3, realisationRow4, realisationRow5, realisationRow6, realisationRow7, realisationRow8, realisationRow9
      )
    );
    // END INSERT REALISATION_ROWS

    // BEGIN INSERT REALISATION_EQUIPMENTS
    val realisationEquipment1 = RealisationEquipment.builder().realisation(realisation1).equipment(equipment2).note("Oprema je uredno očišćena i pospremljena.").build();

    realisationEquipmentRepository.saveAll(
      List.of(
        realisationEquipment1
      )
    );
    // END INSERT REALISATION_EQUIPMENTS

    // BEGIN INSERT REALISATION_ATTACHMENTS
    val realisationAttachment1 = RealisationAttachment.builder().realisation(realisation1).attachment(attachment1).build();

    realisationAttachmentRepository.saveAll(
      List.of(
      )
    );
    // END INSERT REALISATION_ATTACHMENTS

    // BEGIN INSERT REALISATION_AGENTS
    val realisationAgent1 = RealisationAgent.builder().realisation(realisation1).agent(agent1).agentQuantity(BigDecimal.valueOf(32)).agentUnitOfMeasure(unitOfMeasure2).waterQuantity(BigDecimal.valueOf(12)).waterUnitOfMeasure(unitOfMeasure1).costPerUnitOfMeasure(BigDecimal.valueOf(5.20)).note("Stavljeno malo više sredstva zbog nepreciznosti mjerice.").build();

    realisationAgentRepository.saveAll(
      List.of(
        realisationAgent1
      )
    );
    // END INSERT REALISATION_AGENTS

    // BEGIN INSERT HARVESTED_FRUIT_CLASSES
    val harvestedFruitClass1 = HarvestedFruitClass.builder().name(HarvestedFruitClasses.I.name()).displayName(HarvestedFruitClasses.I.displayName()).number(HarvestedFruitClasses.I.number()).build();
    val harvestedFruitClass2 = HarvestedFruitClass.builder().name(HarvestedFruitClasses.II.name()).displayName(HarvestedFruitClasses.II.displayName()).number(HarvestedFruitClasses.II.number()).build();
    val harvestedFruitClass3 = HarvestedFruitClass.builder().name(HarvestedFruitClasses.III.name()).displayName(HarvestedFruitClasses.III.displayName()).number(HarvestedFruitClasses.III.number()).build();
    val harvestedFruitClass4 = HarvestedFruitClass.builder().name(HarvestedFruitClasses.VI.name()).displayName(HarvestedFruitClasses.VI.displayName()).number(HarvestedFruitClasses.VI.number()).build();
    val harvestedFruitClass5 = HarvestedFruitClass.builder().name(HarvestedFruitClasses.V.name()).displayName(HarvestedFruitClasses.V.displayName()).number(HarvestedFruitClasses.V.number()).build();
    val harvestedFruitClass6 = HarvestedFruitClass.builder().name(HarvestedFruitClasses.IV.name()).displayName(HarvestedFruitClasses.IV.displayName()).number(HarvestedFruitClasses.IV.number()).build();

    harvestedFruitClassRepository.saveAll(
      List.of(
        harvestedFruitClass1, harvestedFruitClass2, harvestedFruitClass3, harvestedFruitClass4, harvestedFruitClass5, harvestedFruitClass6
      )
    );
    // END INSERT HARVESTED_FRUIT_CLASSES

    // BEGIN INSERT REALISATION_HARVESTS
    val realisationHarvest1 = RealisationHarvest.builder().realisation(realisation2).fruitCultivar(fruitCultivar1).harvestedFruitClass(harvestedFruitClass1).quantity(BigDecimal.valueOf(30)).unitOfMeasure(unitOfMeasure8).build();

    realisationHarvestRepository.saveAll(
      List.of(
        realisationHarvest1
      )
    );
    // END INSERT REALISATION_HARVESTS

  }

}
