package com;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

public class ActivitiTest  {

    /**
     * 部署流程定义
     */
    @Test
    public void deploymentProcessDefinition(){
        //创建ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取RepositoryService对象
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //进行部署
        Deployment deployment = repositoryService
                .createDeployment()
                .name("测试入门案例")
                .addClasspathResource("bpmn/hello.bpmn")
                .deploy();
        //输出部署的一些信息
        System.out.println("流程部署ID:"+deployment.getId());
        System.out.println("流程部署名称:"+deployment.getName());
    }

    /**
     * 启动流程实例
     */
    @Test
    public void startProcessInstance(){
        //获取与正在执行的流程示例和执行对象相关的Service
        ProcessInstance processInstance = ProcessEngines.getDefaultProcessEngine().getRuntimeService()
                //使用流程定义的key启动实例，key对应bpmn文件中id的属性值，默认按照最新版本流程启动
                .startProcessInstanceByKey("myProcess_1");
        System.out.println("流程实例ID:"+processInstance.getId());
        System.out.println("流程实例定义ID:"+processInstance.getProcessDefinitionId());
    }

    /**
     * 查询当前的个人任务
     */
    @Test
    public void findPersonalTask(){
        //与正在执行的任务相关的Service
        List<Task> list = ProcessEngines.getDefaultProcessEngine().getTaskService()
                .createTaskQuery()  //创建查询任务对象
//                .taskAssignee("UserTask")     //指定个人任务查询，指定办理人
                .list();
        if(list != null && list.size() > 0){
            for(Task task : list){
                System.out.println("任务ID" + task.getId());
                System.out.println("任务名称" + task.getName());
                System.out.println("任务创建时间" + task.getCreateTime());
                System.out.println("任务指定人" + task.getAssignee());
                System.out.println("任务流程实例ID" + task.getProcessInstanceId());
                System.out.println("任务执行ID" + task.getExecutionId());
                System.out.println("任务流程定义ID" + task.getProcessDefinitionId());
            }
        }
    }

    /**
     * 完成指定任务
     */
    @Test
    public void completePersonalTask(){
        // 对应task表的id
        ProcessEngines.getDefaultProcessEngine()
                .getTaskService()
                .complete("2505");
    }
}
