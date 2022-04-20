package com.huitdduru.madduru.mypage.service;

import com.huitdduru.madduru.mypage.payload.request.IntroRequest;
import com.huitdduru.madduru.mypage.payload.response.DiaryResponse;
import com.huitdduru.madduru.mypage.payload.response.MyInfoResponse;
import com.huitdduru.madduru.s3.FileUploader;
import com.huitdduru.madduru.security.auth.AuthenticationFacade;
import com.huitdduru.madduru.user.entity.User;
import com.huitdduru.madduru.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;
    private final FileUploader fileUploader;

    @Override
    public void updateIntroduction(IntroRequest introRequest) {
        User user = authenticationFacade.getUser().setIntro(introRequest.getIntro());
        userRepository.save(user);
    }

    @Override
    public void updateProfileImage(MultipartFile image) throws IOException {
        String uuid = UUID.randomUUID().toString();
        User user = authenticationFacade.getUser();

        String imagePath = image != null ? fileUploader.uploadFile(image, uuid) : null;

        fileUploader.removeFile(user.getImagePath());

        userRepository.save(user.setImagePath(imagePath));
    }

    @Override
    public void unregister() {
        userRepository.save(
                authenticationFacade.getUser().unregister()
        );
    }

    @Override
    public List<DiaryResponse> queryDiaryList() {
        //TODO
        return null;
    }

    @Override
    public MyInfoResponse queryMyInfo() {
        User user = authenticationFacade.getUser();

        return MyInfoResponse.of(user);
    }
}
