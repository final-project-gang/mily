package com.mily.user;

import com.mily.base.rsData.RsData;
import com.mily.estimate.Estimate;
import com.mily.estimate.EstimateRepository;
import com.mily.standard.util.Ut;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MilyUserService {
    private final MilyUserRepository milyUserRepository;
    private final LawyerUserRepository lawyerUserRepository;
    private final EstimateRepository estimateRepository;
    private final PasswordEncoder passwordEncoder;

<<<<<<< HEAD


=======
>>>>>>> 2d6423cc72e2a42843ef688d4d7e1c14dbe22566
    @Transactional
    public RsData<MilyUser> userSignup(String userLoginId, String userPassword, String userNickName, String userName, String userEmail, String userPhoneNumber, String userDateOfBirth, String area) {
        if (findByUserLoginId(userLoginId).isPresent()) {
            return RsData.of("F-1", "%s은(는) 이미 사용 중인 아이디입니다.".formatted(userLoginId));
        }
        if (findByUserNickName(userNickName).isPresent()) {
            return RsData.of("F-1", "%s은(는) 이미 사용 중인 닉네임입니다.".formatted(userNickName));
        }
        if (findByUserEmail(userEmail).isPresent()) {
            return RsData.of("F-1", "%s은(는) 이미 인증 된 이메일입니다.".formatted(userEmail));
        }
        if (findByUserPhoneNumber(userPhoneNumber).isPresent()) {
            return RsData.of("F-1", "%s은(는) 이미 인증 된 전화번호입니다.".formatted(userPhoneNumber));
        }

        LocalDateTime now = LocalDateTime.now();

        MilyUser mu = MilyUser
                .builder()
                .userLoginId(userLoginId)
                .userPassword(passwordEncoder.encode(userPassword))
                .userNickName(userNickName)
                .userName(userName)
                .userEmail(userEmail)
                .userPhoneNumber(userPhoneNumber)
                .role("member")
                .userDateOfBirth(userDateOfBirth)
                .userCreateDate(now)
                .build();

        mu = milyUserRepository.save(mu);
        return RsData.of("S-1", "MILY 회원이 되신 것을 환영합니다!", mu);
    }

    @Transactional
    public RsData<LawyerUser> lawyerSignup(String major, String introduce, String officeAddress, String licenseNumber, String area, MilyUser milyUser) {
        milyUser.setRole("waiting");
        LawyerUser lu = LawyerUser
                .builder()
                .major(major)
                .introduce(introduce)
                .officeAddress(officeAddress)
                .licenseNumber(licenseNumber)
                .area(area)
                .milyUser(milyUser)
                .build();

        lu = lawyerUserRepository.save(lu);
        return RsData.of("S-1", "변호사 가입 신청을 완료하였습니다.", lu);
    }

    public Optional<MilyUser> findByUserLoginId(String userLoginId) {
        return milyUserRepository.findByUserLoginId(userLoginId);
    }

    public Optional<MilyUser> findByUserNickName(String userNickName) {
        return milyUserRepository.findByUserNickName(userNickName);
    }

    public Optional<MilyUser> findByUserEmail(String userEmail) {
        System.out.println("user33333 : " + milyUserRepository.findByUserEmail(userEmail));
        return milyUserRepository.findByUserEmail(userEmail);
    }

    public Optional<MilyUser> findByUserPhoneNumber(String userPhoneNumber) {
        return milyUserRepository.findByUserPhoneNumber(userPhoneNumber);
    }

    public Optional<MilyUser> findById(long id) {
        return milyUserRepository.findById(id);
    }

    public RsData checkUserLoginIdDup (String userLoginId) {
        if ( findByUserLoginId(userLoginId).isPresent() ) return RsData.of("F-1", "%s(은)는 이미 사용 중인 아이디입니다.".formatted(userLoginId));

        return RsData.of("S-1", "%s(은)는 사용 가능한 아이디입니다.".formatted(userLoginId));
    }

    public RsData checkUserNickNameDup (String userNickName) {
        if ( findByUserNickName(userNickName).isPresent() ) return RsData.of("F-1", "%s(은)는 이미 사용 중인 닉네임입니다.".formatted(userNickName));

        return RsData.of("S-1", "%s(은)는 사용 가능한 닉네임입니다.".formatted(userNickName));
    }

    public RsData checkUserEmailDup (String userEmail) {
        if ( findByUserEmail(userEmail).isPresent() ) return RsData.of("F-1", "%s(은)는 이미 인증 된 이메일입니다.".formatted(userEmail));

        return RsData.of("S-1", "%s(은)는 사용 가능한 이메일입니다.".formatted(userEmail));
    }

    public RsData checkUserPhoneNumberDup (String userPhoneNumber) {
        if ( findByUserPhoneNumber(userPhoneNumber).isPresent() ) return RsData.of("F-1", "%s(은)는 이미 인증 된 전화번호입니다.".formatted(userPhoneNumber));

        return RsData.of("S-1", "%s(은)는 사용 가능한 전화번호입니다.".formatted(userPhoneNumber));
    }

<<<<<<< HEAD
=======
    @Transactional
    public void sendEstimate(String category, String categoryItem, MilyUser milyUser) {
        Estimate estimate = new Estimate();
        estimate.setCategory(category);
        estimate.setCategoryItem(categoryItem);
        estimate.setName(milyUser.getUserName());
        estimate.setBirth(milyUser.getUserDateOfBirth());
        estimate.setPhoneNumber(milyUser.getUserPhoneNumber());
        estimate.setMilyUser(milyUser);
        estimate.setCreateDate(LocalDateTime.now());
        this.estimateRepository.save(estimate);
    }

    public MilyUser getUser(String userName) {
        Optional<MilyUser> milyUser = milyUserRepository.findByUserName(userName);
        if (milyUser.isPresent()) {
            return milyUser.get();
        } else {
            throw new Ut.DataNotFoundException("유저 정보가 없습니다.");
        }
    }

    public boolean isAdmin(String userLoginId) {
        return milyUserRepository.findByUserLoginId(userLoginId)
                .map(MilyUser::getUserLoginId)
                .filter(loginId -> loginId.equals("admin123"))
                .isPresent();
    }

    public List<MilyUser> getWaitingLawyerList() {
        List<MilyUser> lawyerUsers = milyUserRepository.findByRole("waiting");
        if (lawyerUsers.isEmpty()) {
            throw new Ut.DataNotFoundException("승인 대기중인 변호사 목록이 없습니다.");
        }
        return lawyerUsers;
    }

    @Transactional
    public void approveLawyer(long id, String userLoginId) {
        if (!isAdmin(userLoginId)) {
            throw new Ut.UnauthorizedException("승인 권한이 없습니다.");
        }

        Optional<MilyUser> optionalLawyer = milyUserRepository.findById(id);

        if (optionalLawyer.isPresent()) {
            MilyUser lawyer = optionalLawyer.get();
            if ("waiting".equals(lawyer.getRole())) {
                lawyer.setRole("approve");
                milyUserRepository.save(lawyer);
            } else {
                throw new Ut.InvalidDataException("선택된 변호사는 대기 중인 상태가 아닙니다.");
            }
        } else {
            throw new Ut.DataNotFoundException("변호사를 찾을 수 없습니다.");
        }
    }

    public Optional<MilyUser> findUserByEmail(String userEmail) {
        return findByUserEmail(userEmail);
    }
>>>>>>> 2d6423cc72e2a42843ef688d4d7e1c14dbe22566

    public MilyUser findUserLoginIdByEmail(String userEmail) {
        return milyUserRepository.findUserLoginIdByEmail(userEmail);
    }

    public Optional<MilyUser> findByuserLoginIdAndEmail(String userLoginId, String email) {
        return milyUserRepository.findByUserLoginIdAndUserEmail(userLoginId, email);
    }

<<<<<<< HEAD

=======
    public static void sendTempPasswordToEmail(MilyUser member) {
    }
>>>>>>> 2d6423cc72e2a42843ef688d4d7e1c14dbe22566

    public MilyUser getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof User user) {
            return milyUserRepository.findByUserLoginId(user.getUsername()).orElse(null);
        }
        return null;
    }

    @Transactional
    public RsData<MilyUser> getPoint(MilyUser isLoginedUser, String orderName) {
        // isLoginedUser 의 milyPoint 값을 가져온다.
        int milyPoint = isLoginedUser.getMilyPoint();

        // 가져온 milyPoint 값에 orderName을 더한다.
        milyPoint += Integer.parseInt(orderName.substring(7));

        // repository에 저장한다.
        isLoginedUser.setMilyPoint(milyPoint);
        milyUserRepository.save(isLoginedUser);

        return RsData.of("S-1", "포인트 지급", null);
    }
<<<<<<< HEAD

    /*public void sendTempPasswordToEmail(MilyUser member) {
    }
*/
    public Optional<MilyUser> findByUserLoginIdAndEmail(String userLoginId, String userEmail) {
        return Optional.empty();

    }

    public void sendTempPasswordToEmail(String email, String tempPassword) {
    }

    public String generateTempPassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    public void updateUserPassword(long id, String tempPassword) {
    }
}
=======
>>>>>>> 2d6423cc72e2a42843ef688d4d7e1c14dbe22566

    public MilyUser getLawyer(String UserLoginId, String role) {
        Optional<MilyUser> lawyerUser = milyUserRepository.findByUserLoginIdAndRole(UserLoginId, role);
        if (lawyerUser.isPresent()) {
            return lawyerUser.get();
        } else {
            throw new Ut.DataNotFoundException("변호사 정보가 없습니다.");
        }
    }

    public List<Estimate> getEstimate(String category, String area) {
        List<Estimate> estimate = estimateRepository.findByCategoryAndArea(category, area);
        if (!estimate.isEmpty()) {
            return estimate;
        } else {
            List<Estimate> estimateArea = estimateRepository.findByArea(area);
            if (!estimateArea.isEmpty()) {
                return estimateArea;
            } else {
                throw new Ut.DataNotFoundException("견적서에 해당되는 변호사가 없습니다.");
            }
        }
    }
}