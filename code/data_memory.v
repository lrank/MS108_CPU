`timescale 1ns/10ps
module data_memory (reset0,clock,data,addr,write_enable,outp,valid,visit,vivi, end_signal_i);
	input wire clock;
	input wire reset0;
	input wire [31:0] data,addr;
	input wire write_enable;
	input wire visit;
	output reg [31:0] outp;
	output reg valid;
	output reg vivi;
	
	input wire end_signal_i;
	integer fp_r, fp_w;
	reg [31:0] op, ed;
	integer i, cnt;
	always @(end_signal_i)
	begin
	if (end_signal_i == 1'b1)
	begin
		fp_r = $fopen("address.txt", "r");
		fp_w = $fopen("memory_data_out.txt", "w");
		cnt = $fscanf(fp_r, "%d %d", op, ed);
		$fwrite(fp_w,"@%d\n",op);
		for (i = op; i <= ed; i = i + 4)
		  begin
			$fwrite(fp_w, "%h %h %h %h\n",mem[i],mem[i+1],mem[i+2],mem[i+3]);
		  end
		$fclose(fp_r);
		$fclose(fp_w);
		$finish;
	end
	end
	
	parameter SIZE=262144;
	reg[7:0] mem [0:SIZE-1];
	
	initial
	begin
	  vivi=0;
	  for (i=0;i<SIZE;i=i+1)
	  begin
	    mem[i]=8'b0;
	  end
	  $readmemh("ram_data.txt",mem);
	end
	
	always @(/*reset0 or data or addr or write_enable or visit*/negedge clock)
	begin
	  if (visit==1'b1) begin
	  if (reset0==1'b1)
	  begin
	    for (i=0;i<SIZE;i=i+1)
	      mem[i]=32'b0;
	    //$readmemb("ram_data.txt",mem);
	  end
	  else
	  begin
	    if (addr%4==0) assign valid=1;
	    else assign valid=0;
	    if (write_enable && valid)
	    begin
	      mem[addr]=data[31:24];
	      mem[addr+1]=data[23:16];
	      mem[addr+2]=data[15:8];
	      mem[addr+3]=data[7:0];
	      //$monitor("%b%b%b%b",mem[addr+3],mem[addr+2],mem[addr+1],mem[addr]);
	    end
	    if (valid)
	    begin
	      outp={mem[addr],mem[addr+1],mem[addr+2],mem[addr+3]};
	    end
	    else outp=32'b0;
	  end 
	  vivi=~vivi;
	  end
	end
	
endmodule 