package com.guigu;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guigu.bean.User;
import com.guigu.mapper.UserMapper;
import com.guigu.sevice.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description       ：
 *
 * @author ：lvhan
 * @version ：1.0
 * @since ：2020/9/28 9:51
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

    @Test
    public void selectByPrimaryKey() {
        User user = userMapper.selectByPrimaryKey(3L);
        System.err.println(user);
    }

    @Test
    public void selectById() {
        User user = userMapper.selectById(1L);
        System.err.println(user);
    }


    @Test
    public void testMyPage() {
        Page<User> objectPage = new Page<>();
        objectPage.setCurrent(3);
        objectPage.setSize(2);
        User user = new User();
        user.setAge(20);
        user.setName("zhangsan");
        QueryWrapper<Object> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(true,wrapper->wrapper.ge("age",user.getAge()).or().eq("name", user.getName()));
//        queryWrapper.and(StringUtils.isNotBlank(user.getName()), wrapper -> wrapper.eq("name", user.getName()));
        Page<User> users = userMapper.selectByMajunPage(objectPage, queryWrapper);
        //原生自带的
//        Page<User> users = userMapper.selectPage(objectPage, queryWrapper);
//        System.err.println(users.getRecords());
    }

    @Test
    public void testMyPage2() {
        IPage<User> objectPage = new Page<>();
        objectPage.setCurrent(1);
        objectPage.setSize(2);
        User user = new User();
        user.setAge(20);
        user.setName("zhangsan");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(true,wrapper->wrapper.ge("age",user.getAge()).or().eq("name", user.getName()));
//        queryWrapper.and(StringUtils.isNotBlank(user.getName()), wrapper -> wrapper.eq("name", user.getName()));
//        Page<User> users = userMapper.selectByMajunPage(objectPage, queryWrapper);
        userMapper.selectPage((Page<User>) objectPage, queryWrapper);
        //原生自带的
//        Page<User> users = userMapper.selectPage(objectPage, queryWrapper);
//        System.err.println(users.getRecords());
    }

    @Test
    public void testUserPage() {
        IPage<User> objectPage = new Page<>();
        objectPage.setCurrent(1);
        objectPage.setSize(2);
        User user = new User();
        user.setAge(20);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(user);
        IPage<User> userIPage = userMapper.selectPage(objectPage, queryWrapper);
        System.err.println(userIPage);
    }

    @Test
    public void testUserMyPage() {
        Page<Object> objectPage = new Page<>();
        objectPage.setCurrent(1);
        objectPage.setSize(2);
        User user = new User();
        user.setAge(20);
        List<User> users = userMapper.selectByUser(user);
        System.err.println(users);
    }


    @Test
    public void test() {
        List<User> tbUsers = userMapper.selectList(null);
        tbUsers.forEach(System.out::println);
    }




    /**
     * 添加
     */
    @Test
    public void add() {
        User user = new User();
        user.setAge(20);
        user.setEmail("lomonkey@aliyun.com");
        user.setName("Lomonkey");
        int result = userMapper.insert(user);
        System.out.println(user);
        System.out.println("result = " + result);
    }

    /**
     * 更新
     */
    @Test
    public void updateTest() {
        User user = new User();
        user.setId(1L);
        user.setName("updateName");
        int update = userMapper.updateById(user);
        System.out.println(user);
        System.out.println("update = " + update);
    }

    /**
     * 更新
     */
    @Test
    public void updateTest2() {
        User user = new User();
        user.setId(3L);
        user.setIsDeleted("1");
//        user.setName("updateName");
        int update = userMapper.updateById(user);
        System.out.println(user);
        System.out.println("update = " + update);
    }

    /**
     * 普通删除
     */
    @Test
    public void deleteTest() {
        int delete = userMapper.deleteById(1310419023559942154L);
        System.out.println("delete = " + delete);
    }

    /**
     * 普通删除
     */
    @Test
    public void deleteTest2() {
        int delete = userMapper.deleteById(3L);
        System.out.println("delete = " + delete);
    }

    /**
     * 根据 map 中条件删除
     */
    @Test
    public void deleteByMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "Lomonkey");
        int result = userMapper.deleteByMap(map);
        System.out.println("result = " + result);
    }

    /**
     * 根据 ids 批量删除
     */
    @Test
    public void deleteBatchIds() {
        // 根据 ids 删除
        userMapper.deleteBatchIds(Arrays.asList(1310419023559942148L ,1310419023559942146L ,1310419023559942145L));
    }

    /**
     * 根据条件删除
     */
    @Test
    public void deleteByWrapper() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // 姓名 %Lomonkey% 的
        wrapper
            .like("name", "Lomonkey")
            // 邮箱 %lv 的
            .likeLeft("email", "lv")
            // id >= 30
            .ge("id", 30)
            // age 在 30-50 之间
            .between("age", 30, 50);
        userMapper.delete(wrapper);
    }

    /**
     * 查询所有, 根据条件查询
     */
    @Test
    public void selectList() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.notLike("name", "Jack")
                .in("id", Arrays.asList(1, 2, 3, 4, 5))
                .orderByAsc("id")
                // 结果只显示 name 和 email
                .select("name", "email");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    /**
     * 查询所有, 根据条件查询
     */
    @Test
    public void selectPageList() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        wrapper.notLike("name", "Jack")
//                .in("id", Arrays.asList(1, 2, 3, 4, 5))
//                .orderByAsc("id")
                // 结果只显示 name 和 email
//        wrapper.select("name", "email");
        Page<User> objectPage = new Page<>();
        objectPage.setCurrent(3L);
        objectPage.setSize(2L);
        Page<User> page = userService.page(objectPage, wrapper);
        System.err.println(page);
//        users.forEach(System.out::println);
    }

    /**
     * 查询所有, 根据条件查询
     */
    @Test
    public void selectUserServiceList() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.notLike("name", "Jack")
                .in("id", Arrays.asList(1, 2, 3, 4, 5))
                .orderByAsc("id")
                // 结果只显示 name 和 email
                .select("name", "email");
//        List<User> users = userService.list(wrapper);
//        users.forEach(System.out::println);
        List<Map<String, Object>> maps = userService.listMaps(wrapper);
        maps.forEach(stringObjectMap -> {
//            System.out.println(stringObjectMap.entrySet());
            System.out.println(stringObjectMap.keySet());
            System.err.println(stringObjectMap);
        });
    }

    /**
     * 批量id查询
     */
    @Test
    public void selectBatchIds() {
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);
    }

    /**
     * 通过 id 查询
     */
    @Test
    public void testselectById() {
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }

    /**
     * 通过 Map 查询
     */
    @Test
    public void selectByMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "Jack");
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    /**
     * 查询总数 count
     */
    @Test
    public void selectCount() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.in("id", Arrays.asList(1, 2, 3, 4, 5));
        Integer integer = userMapper.selectCount(wrapper);
        System.out.println("integer = " + integer);
    }

    /**
     * 分页查找
     */
    @Test
    public void selectPage() {
        // 复杂查询
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("name", "Lomonkey");

        // 配置分页
        Page<User> page = new Page<>(1, 3);

        // 查询
        userMapper.selectPage(page, wrapper);
        System.out.println(page);
    }

    /**
     * 添加用户
     */
    @Test
    public void insertUser() {
        User user = new User();
        user.setName("Monkey004");
        user.setAge(25);
        userMapper.insert(user);
        System.out.println(user);
    }

    /**
     * 修改用户
     */
    @Test
    public void updateUser() {
        User user = new User();
        user.setId(8L);
        user.setName("zhangsan5");
        user.setAge(35);
        userMapper.updateById(user);
        System.out.println(user);
    }

    /**
     * 乐观锁测试, 乐观锁 测试必须携带 version否则无效
     *
     * 结果 线程二修改成功, 线程一 修改失败
     */
    @Test
    public void OptimisticLocker() {

        // 线程一
        User user1 = userMapper.selectById(1L);
        // 获取 version
        Integer version = user1.getVersion();
        user1.setName("我是线程一修改的name");

        // 线程二
        User user2 = userMapper.selectById(1L);
        // 获取 version
        Integer version2 = user2.getVersion();
        user2.setName("我是线程二修改的name");
        userMapper.updateById(user2);

        userMapper.updateById(user1);

    }

    /**
     * <p>
     * 根据 whereEntity 条件，更新记录
     * </p>
     *
//     * @param entity        实体对象 (set 条件值,不能为 null)
//     * @param updateWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
     */
    @Test
    public void update() {

        //修改值
        User user = new User();
        user.setAge(32);
        user.setName("zhangsan");
        //修改条件s
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
//        userUpdateWrapper.eq("name", "Tom");
        userUpdateWrapper.eq(true,"name", "majun");
        int update = userMapper.update(user, userUpdateWrapper);
        System.out.println(update);
    }

    @Test
    public void update2() {

        //修改值
        User user = new User();
        user.setAge(32);
        user.setName("majun");
        //修改条件s
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq(false,"name", "zhangsan");
        int update = userMapper.update(user, userUpdateWrapper);
        System.out.println(update);
    }

    /**
     * 获取查询条件
     */
    @Test
    public void getQueryWrapper() {
        User user = new User();
        user.setAge(32);
//        user.setEmail("lomonkey@aliyun.com");
        user.setName("zhangsan");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>().setEntity(user);
        QueryWrapper<User> userQueryWrapper = queryWrapper.setEntity(user);
        userQueryWrapper.ge("age",32);
        List<User> users = userMapper.selectList(userQueryWrapper);
//        int result = userMapper.insert(user);
        System.out.println(users);
        System.out.println("result = " + users);
    }

    @Test
    public void testDelete() {
        List<User> users = userMapper.selectList(Wrappers.<User>lambdaQuery().eq(User::getIsDeleted,1));
        System.out.println(users);
    }

    @Test
    public void testLambdaUpdate() {
        LambdaUpdateWrapper<User> luw = Wrappers.lambdaUpdate();
        luw.set(User::getName, "zhangsan")
                .set(User::getAge, 1);
        luw.eq(User::getAge, 20);
        userMapper.update(null, luw);
    }

}
