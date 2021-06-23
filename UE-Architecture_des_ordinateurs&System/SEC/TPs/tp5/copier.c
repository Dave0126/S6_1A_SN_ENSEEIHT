#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#define BUFFER_SIZE 16

int main(int argc,char* argv[])
{
	int fd1, fd2, fd3;
	int wr_ret;
	int rd_ret, rd_ret2;
	char buffer[BUFFER_SIZE];
	char rd_buf[BUFFER_SIZE];
	/*
        正确返回：文件描述符
        错误返回：-1
        参数描述：
        pathname：待打开或创建的文件名
                oflag：如何打开文件，必须包含以下三个中的一个
                             O_RDONLY - 只读方式打开
                             O_WRONLY - 只写方式打开
                             O_RDWR - 读写方式打开
                             还可以按位逻辑加（ | ）组合下列标志
                             O_APPEND - 每次写文件从文末开始
                             O_CREAT - 文件不存在则创建，必须带第三个参数mode设置新文件的访问方式
                             O_EXCL - 检查文件是否存在，以及不存在时创建文件的情况
                             O_TRUNC - 如果文件存在，而且以只写或读写方式打开，将文件长度截至0
                mode：如果用open创建文件就要使用，其用来规定文件所以者、用户组、其它用户的访问权限。逻辑加组合下列常量
                             <sys/stat.h>中定义
                             S_IRUSER - 文件所有者的读权限位
                             S_IWUSER - 文件所有者的写权限位
                             S_IXUSER - 文件所有者的执行权限位
                             S_IRGRP - 文件用户组的读权限位
                             S_IWGRP - 文件用户组的写权限位
                             S_IXGRP - 文件用户组的执行权限位
                             S_IROTH - 文件其它用户的读权限位
                             S_IWOTH - 文件其它用户的写权限位
                             S_IXOTH - 文件其它用户的执行权限位
                             还可以用下列常量设置set _uid位和set_gid位：
                             S_ISUID
                             S_ISGID
	*/

	fd1 = open("source.txt", O_RDONLY | O_CREAT , S_IRUSR | S_IWUSR | S_IROTH);//等价于fd = open("a.txt", O_RDWR | O_CREAT | O_TRUNC, 0x604);
	if(fd1 == -1)
	{
		perror("open file 'source.txt' error:");
		return -1;
	}
	fd2 = open("copy.txt", O_RDWR | O_CREAT | O_TRUNC, S_IRUSR | S_IWUSR | S_IROTH);
	if(fd2 == -1)
	{
		perror("open file 'copy.txt' error:");
		return -1;
	}
	while (rd_ret = read(fd1, buffer, sizeof(buffer))) {
        printf("ReadBuffer from file 'source.txt' : %s\n", buffer);
        write(fd2, buffer, rd_ret);
	}

	close(fd1);
	close(fd2);//关闭文件

	fd3 = open("copy.txt", O_RDONLY, S_IRUSR | S_IWUSR | S_IROTH);
	if(fd3 == -1)
	{
		perror("open file 'copy.txt' error:");
		return -1;
	}
    //rd_ret2 = read(fd3, rd_buf, sizeof(rd_buf));
    printf("The content of file 'copy.txt':\n");
    while (rd_ret2 = read(fd3, rd_buf, sizeof(rd_buf))) {
        printf("%s",rd_buf);
    }
	close(fd3);//关闭文件


	return 0;
}
