// package com.lendbiz.p2p.api.configs;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Map;
// import java.util.stream.Collectors;


// import com.lendbiz.p2p.api.entity.CmdMenuEntity;
// import com.lendbiz.p2p.api.entity.SearchEntity;
// import com.lendbiz.p2p.api.entity.SearchFldEntity;

// import com.lendbiz.p2p.api.entity.TreeCmdMenuEntity;
// import com.lendbiz.p2p.api.repository.CmdMenuRepository;
// import com.lendbiz.p2p.api.repository.SearchFldRepository;
// import com.lendbiz.p2p.api.repository.SearchRepository;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.ApplicationArguments;
// import org.springframework.boot.ApplicationRunner;
// import org.springframework.stereotype.Component;

// @Component
// public class DataLoader implements ApplicationRunner {

//     protected Logger logger = LoggerFactory.getLogger(this.getClass());
//     @Autowired
//     CmdMenuRepository cmdRepo;

//     @Autowired
//     SearchRepository searchRepo;

//     @Autowired
//     SearchFldRepository searchFldRepo;

//     public static List<CmdMenuEntity> lstCmdMenu;

//     public static List<SearchEntity> lstSearch;

//     public static List<SearchFldEntity> lstSearchFld;

//     public static Map<Integer, List<CmdMenuEntity>> cmdMenuByLevel;

//     public static Map<String, List<SearchEntity>> searchByCode;

//     public static Map<String, List<SearchFldEntity>> searchFldByCode;

//     public static List<List<TreeCmdMenuEntity>> treeMenuList = new ArrayList<List<TreeCmdMenuEntity>>();

//     public DataLoader(CmdMenuRepository cmdRepo) {
//         this.cmdRepo = cmdRepo;
//     }

//     @Override
//     public void run(ApplicationArguments args) throws Exception {
//         logger.info("<======== LOAD DATA ===========>");
//         lstCmdMenu = cmdRepo.getAll();
//         lstSearch = searchRepo.getAll();
//         lstSearchFld = searchFldRepo.getAll();
//         treeMenuList = setCmdMenuTreeByUser(lstCmdMenu);
//         setMappingCmdMenuLevel();
//         setMappingSearchCode();
//         setMappingSearchFldCode();
//         logger.info("<======== END LOAD DATA ========>");

//     }

//     public void setMappingCmdMenuLevel() {
//         cmdMenuByLevel = lstCmdMenu.stream().collect(Collectors.groupingBy(CmdMenuEntity::getLev));
//     }

//     public void setMappingSearchCode() {
//         searchByCode = lstSearch.stream().collect(Collectors.groupingBy(SearchEntity::getSearchCode));
//     }

//     public void setMappingSearchFldCode() {
//         searchFldByCode = lstSearchFld.stream().collect(Collectors.groupingBy(SearchFldEntity::getSearchCode));
//     }

//     public static List<List<TreeCmdMenuEntity>> getMenuByCmdCode(List<String> cmdCodes) {
//         List<List<TreeCmdMenuEntity>> lstTreeUserMenu = new ArrayList<>();
//         List<CmdMenuEntity> lstUserMenu = new ArrayList<>();

//         lstCmdMenu.stream().forEach(s -> {
//             cmdCodes.stream().forEach(c -> {
//                 if (s.getCmdId().equalsIgnoreCase(c)) {
//                     lstUserMenu.add(s);
//                 }
//             });
//         });

//         lstTreeUserMenu = setCmdMenuTreeByUser(lstUserMenu);

//         return lstTreeUserMenu;
//     }

//     public static List<List<TreeCmdMenuEntity>> setCmdMenuTreeByUser(List<CmdMenuEntity> lstCmdMenu) {
//         System.out.println("<======== Convert Data ========>");

//         List<List<TreeCmdMenuEntity>> treeUserMenuList = new ArrayList<List<TreeCmdMenuEntity>>();
//         List<TreeCmdMenuEntity> menuTrees = new ArrayList<>();

//         lstCmdMenu.stream().forEach(s -> {
//             TreeCmdMenuEntity newCmdMenu = new TreeCmdMenuEntity();
//             newCmdMenu.setAuthCode(s.getAuthCode());
//             newCmdMenu.setCmdId(s.getCmdId());
//             newCmdMenu.setCmdName(s.getCmdName());
//             newCmdMenu.setEn_cmdName(s.getEn_cmdName());
//             newCmdMenu.setLast(s.getLast());
//             newCmdMenu.setLev(s.getLev());
//             newCmdMenu.setMenucode(s.getMenucode());
//             newCmdMenu.setMenutype(s.getMenutype());
//             newCmdMenu.setModCode(s.getModCode());
//             newCmdMenu.setObjName(s.getObjName());
//             newCmdMenu.setPrId(s.getPrId());
//             newCmdMenu.setTltxCd(s.getTltxCd());

//             menuTrees.add(newCmdMenu);
//         });

//         List<TreeCmdMenuEntity> cmdRoot = new ArrayList<>();
//         menuTrees.stream().forEach(s -> {if (s.getLev() == 1) {cmdRoot.add(s);}});

//         List<TreeCmdMenuEntity> lL2 = new ArrayList<>();
//         menuTrees.stream().forEach(s -> {if (s.getLev() == 2) {lL2.add(s);}});

//         List<TreeCmdMenuEntity> lL3 = new ArrayList<>();
//         menuTrees.stream().forEach(s -> {if (s.getLev() == 3) {lL3.add(s);}});

//         List<TreeCmdMenuEntity> lL4 = new ArrayList<>();
//         menuTrees.stream().forEach(s -> {if (s.getLev() == 4) {lL4.add(s);}});

//         for (TreeCmdMenuEntity root : cmdRoot) {
//             List<TreeCmdMenuEntity> lev2 = new ArrayList<TreeCmdMenuEntity>();

//             for (TreeCmdMenuEntity t : lL2) {
//                 if (t.getPrId().equalsIgnoreCase(root.getCmdId())) {
//                     lev2.add(t);
//                     root.setMenuChildrens(lev2);
//                 }

//                 List<TreeCmdMenuEntity> lev3 = new ArrayList<TreeCmdMenuEntity>();

//                 for (TreeCmdMenuEntity s : lL3) {
//                     if (s.getPrId().equalsIgnoreCase(t.getCmdId())) {
//                         lev3.add(s);
//                         t.setMenuChildrens(lev3);
//                     }

//                     List<TreeCmdMenuEntity> lev4 = new ArrayList<TreeCmdMenuEntity>();

//                     for (TreeCmdMenuEntity j : lL3) {
//                         if (j.getPrId().equalsIgnoreCase(s.getCmdId())) {
//                             lev4.add(j);
//                             s.setMenuChildrens(lev4);
//                         }

//                     }
//                 }
//             }
//         }
            
//         treeUserMenuList.add(cmdRoot);

//         System.out.println("<======== END Convert Data ========>");

//         return treeUserMenuList;

//     }

// }
