#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
 
int main(int argc,char* argv[])
{
	int fd;
	int wr_ret;
	int rd_ret;
	unsigned long file_size;
	char wr_buf[100] = "hello world";
	char rd_buf[100];
 
	fd = open("a.txt", O_RDWR | O_CREAT | O_TRUNC, S_IRUSR | S_IWUSR | S_IROTH);//等价于fd = open("a.txt", O_RDWR | O_CREAT | O_TRUNC, 0x604);
	if(fd == -1)
	{
		perror("open file error:");//只有上面的函数设置了error全局错误号，才可使用，会根据error输出对应的错误信息
		return -1;
	}
	printf("fd = %d\n", fd);
 
	wr_ret = write(fd, wr_buf, sizeof(wr_buf));
	if(wr_ret == -1)
	{
		perror("write file error:");
		return -1;
	}
	printf("wr_ret = %d\n", wr_ret);
 
	lseek(fd, 0, SEEK_SET);//上面的写操作，文件位置偏移量也会相应的移动，此处将文件偏移到文件开始位置，然后才能读取刚刚输入的内容
	rd_ret = read(fd, rd_buf, sizeof(rd_buf));
	if(rd_ret == -1)
	{
		perror("read file error:");
		return -1;
	}
	printf("rd_ret = %d\n",rd_ret);
	printf("content=%s\n", rd_buf);
 
	file_size = lseek(fd, 0, SEEK_END);
	printf("file_size = %lu\n", file_size);
 
	close(fd);//关闭文件
 
	return 0;
}
