int main()
{
	int a[30][30],b[30][30],c[30][30];
	int i,j,k;
	
	/*a[0][0]=1;
	a[0][1]=2;
	a[0][2]=3;
	a[1][0]=2;
	a[1][1]=3;
	a[1][2]=1;
	b[0][0]=1;
	b[0][1]=1;
	b[1][0]=0;
	b[1][1]=1;
	b[2][0]=2;
	b[2][1]=1;
	c[0][0]=0;
	c[0][1]=0;
	c[1][0]=0;
	c[1][1]=0;*/
	
	for(i=0;i<30;i++)
	{
		for(j=0;j<30;j++)
		{
			for(k=0;k<30;k++)
			{
				c[i][k] += a[i][j]*b[j][k];
			}
		}
	}

	/*for(i=0;i<2;i++)
	{
		for(j=0;j<2;j++)
		{
			printf("%d ",c[i][j]);
		}
	}*/
	return 0;
}
