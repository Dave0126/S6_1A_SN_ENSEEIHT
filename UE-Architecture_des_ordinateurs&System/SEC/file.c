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
 
	fd = open("a.txt", O_RDWR | O_CREAT | O_TRUNC, S_IRUSR | S_IWUSR | S_IROTH);//�ȼ���fd = open("a.txt", O_RDWR | O_CREAT | O_TRUNC, 0x604);
	if(fd == -1)
	{
		perror("open file error:");//ֻ������ĺ���������errorȫ�ִ���ţ��ſ�ʹ�ã������error�����Ӧ�Ĵ�����Ϣ
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
 
	lseek(fd, 0, SEEK_SET);//�����д�������ļ�λ��ƫ����Ҳ����Ӧ���ƶ����˴����ļ�ƫ�Ƶ��ļ���ʼλ�ã�Ȼ����ܶ�ȡ�ո����������
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
 
	close(fd);//�ر��ļ�
 
	return 0;
}
