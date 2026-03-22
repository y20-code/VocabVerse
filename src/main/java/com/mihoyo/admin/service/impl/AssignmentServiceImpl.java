package com.mihoyo.admin.service.impl;

import com.mihoyo.admin.dto.ClassDTO;
import com.mihoyo.admin.dto.CreateAssignmentReqDTO;
import com.mihoyo.admin.entity.AssignmentEntity;
import com.mihoyo.admin.entity.AssignmentProgressEntity;
import com.mihoyo.admin.entity.AssignmentWordEntity;
import com.mihoyo.admin.entity.ClassEntity;
import com.mihoyo.admin.mapper.AssignmentMapper;
import com.mihoyo.admin.mapper.DashboardMapper; // 🌟 引入咱们查班级的 Mapper
import com.mihoyo.admin.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Autowired
    private DashboardMapper dashboardMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createAssignments(CreateAssignmentReqDTO reqDTO, String teacherId) {


        if (reqDTO.getClassIds() == null || reqDTO.getClassIds().isEmpty()) {
            throw new RuntimeException("目标班级不能为空");
        }
        if (reqDTO.getBaseWordIds() == null || reqDTO.getBaseWordIds().isEmpty()) {
            throw new RuntimeException("基础词库不能为空");
        }


        List<ClassEntity> myRealClasses = dashboardMapper.selectClassesByTeacherId(teacherId);
        if (myRealClasses == null || myRealClasses.isEmpty()) {
            throw new RuntimeException("越权操作：您名下没有任何班级！");
        }


        Set<String> mySafeClassIds = myRealClasses.stream()
                .map(ClassEntity::getId)
                .collect(Collectors.toSet());

        List<String> validClassIds = reqDTO.getClassIds().stream()
                .filter(mySafeClassIds::contains)
                .collect(Collectors.toList());

        if (validClassIds.isEmpty()) {
            throw new RuntimeException("极其严重的越权警告：请求中的班级均不属于您！");
        }


        LocalDateTime deadline = reqDTO.getDeadline() != null ? reqDTO.getDeadline() : LocalDateTime.now().plusDays(1);
        String titlePrefix = reqDTO.getTitle() != null ? reqDTO.getTitle() : "日常词汇任务";


        for (String classId : validClassIds) {

            String assignmentId = "assn-" + UUID.randomUUID().toString().substring(0, 8);

            AssignmentEntity assignment = new AssignmentEntity();
            assignment.setId(assignmentId);
            assignment.setClassId(classId); // 这里绝对安全了！
            assignment.setTitle(titlePrefix);
            assignment.setDeadline(deadline);
            assignment.setCreatedAt(LocalDateTime.now());

            assignmentMapper.insertAssignment(assignment);

            Set<String> finalWordIds = new HashSet<>(reqDTO.getBaseWordIds());

            if (Boolean.TRUE.equals(reqDTO.getIsPersonalized())) {
                List<String> weakWords = assignmentMapper.selectClassWeakWordIds(classId);
                if (weakWords != null && !weakWords.isEmpty()) {
                    finalWordIds.addAll(weakWords);
                }
            }

            List<AssignmentWordEntity> wordEntities = new ArrayList<>();
            for (String wordId : finalWordIds) {
                AssignmentWordEntity aw = new AssignmentWordEntity();
                aw.setId("aw-" + UUID.randomUUID().toString().substring(0, 8));
                aw.setAssignmentId(assignmentId);
                aw.setWordId(wordId);
                wordEntities.add(aw);
            }
            if (!wordEntities.isEmpty()) {
                assignmentMapper.batchInsertAssignmentWords(wordEntities);
            }

            List<String> studentIds = assignmentMapper.selectStudentIdsByClassId(classId);

            if (studentIds != null && !studentIds.isEmpty()) {
                List<AssignmentProgressEntity> progressEntities = new ArrayList<>();
                for (String studentId : studentIds) {
                    AssignmentProgressEntity ap = new AssignmentProgressEntity();
                    ap.setId("prog-" + UUID.randomUUID().toString().substring(0, 8));
                    ap.setAssignmentId(assignmentId);
                    ap.setStudentId(studentId);
                    ap.setStatus("pending");
                    ap.setProgress(0);
                    progressEntities.add(ap);
                }
                assignmentMapper.batchInsertAssignmentProgress(progressEntities);
            }
        }
    }

    @Override
    public List<ClassDTO> getClasses(String teacherId) {
        return assignmentMapper.selectClassesByTeacherId(teacherId);
    }


}