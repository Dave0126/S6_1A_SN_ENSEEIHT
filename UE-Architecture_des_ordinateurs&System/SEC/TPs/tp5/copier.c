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
        ��ȷ���أ��ļ�������
        ���󷵻أ�-1
        ����������
        pathname�����򿪻򴴽����ļ���
                oflag����δ��ļ�������������������е�һ��
                             O_RDONLY - ֻ����ʽ��
                             O_WRONLY - ֻд��ʽ��
                             O_RDWR - ��д��ʽ��
                             �����԰�λ�߼��ӣ� | ��������б�־
                             O_APPEND - ÿ��д�ļ�����ĩ��ʼ
                             O_CREAT - �ļ��������򴴽������������������mode�������ļ��ķ��ʷ�ʽ
                             O_EXCL - ����ļ��Ƿ���ڣ��Լ�������ʱ�����ļ������
                             O_TRUNC - ����ļ����ڣ�������ֻд���д��ʽ�򿪣����ļ����Ƚ���0
                mode�������open�����ļ���Ҫʹ�ã��������涨�ļ������ߡ��û��顢�����û��ķ���Ȩ�ޡ��߼���������г���
                             <sys/stat.h>�ж���
                             S_IRUSER - �ļ������ߵĶ�Ȩ��λ
                             S_IWUSER - �ļ������ߵ�дȨ��λ
                             S_IXUSER - �ļ������ߵ�ִ��Ȩ��λ
                             S_IRGRP - �ļ��û���Ķ�Ȩ��λ
                             S_IWGRP - �ļ��û����дȨ��λ
                             S_IXGRP - �ļ��û����ִ��Ȩ��λ
                             S_IROTH - �ļ������û��Ķ�Ȩ��λ
                             S_IWOTH - �ļ������û���дȨ��λ
                             S_IXOTH - �ļ������û���ִ��Ȩ��λ
                             �����������г�������set _uidλ��set_gidλ��
                             S_ISUID
                             S_ISGID
	*/

	fd1 = open("source.txt", O_RDONLY | O_CREAT , S_IRUSR | S_IWUSR | S_IROTH);//�ȼ���fd = open("a.txt", O_RDWR | O_CREAT | O_TRUNC, 0x604);
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
	close(fd2);//�ر��ļ�

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
	close(fd3);//�ر��ļ�


	return 0;
}
