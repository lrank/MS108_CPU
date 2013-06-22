`timescale 1ns/10ps
module cache (clock,
  reset,
  write,
  read,
  address,
  data_in,
  data_out,
  hit);  // 2-way associative, LRU replacement policy.

   parameter DATAWIDTH=32; //Width of data
   parameter INDEX=12; //2^12=4k lines (1 word/set)

   // ---------------------------- Input ports ----------------------------
   input clock;
   input reset;
   input write; //write to Cache?
   input read;
   input data_in,address; //Input Data

   // ---------------------------- Output ports ----------------------------
   output data_out; //Output Data
   output hit; //hit?

   // ---------------------------- Type of Input ports ----------------------------
   wire clock;
   wire reset;
   wire write; //write signal
   wire read;
   wire [DATAWIDTH-1:0]       data_in,address;
   wire [INDEX-1:0] 	      Index;
   wire [DATAWIDTH-INDEX-1:0] Tag_in;
   wire 		      Valid_in;

   // ---------------------------- Type of Output ports ----------------------------
   reg [DATAWIDTH-1:0] 	      data_out;
   reg 			      hit;
   reg          dirty;
   reg [DATAWIDTH-INDEX-1:0]  Tag_out;
   reg 			      Valid,Valid2;

   //reg [0:4095] 	      LRU; //Least recently use_timesd set in a line;
   //reg [105:0] 		      TEMPO;
   
   wire write_enable;
   assign write_enable=(!read && write);
   //reg [31:0] save_outp;
   wire [31:0] save_outp;
   wire valid;
   wire visit;
   wire vivi;
   assign visit=(read || write);
   data_memory ps(.reset0(reset),.clock(clock),.data(data_in),.addr(address),.write_enable(write_enable),.outp(save_outp),.valid(valid),.visit(visit),.vivi(vivi));
      //data_memory d_memory(reset,clock,data_in,address,write_enable,outp,valid);
   
   parameter 		      SIZE=54; // Valid + Dirty + Tag + Data

   reg [SIZE-1:0] 	      memcache [0:8191]; //Lines 0-8191
   reg [SIZE-1:0]  temp,temp2;
   reg [SIZE-1:0]	use_times[0:8191];

   integer 		      i;

   assign 		      Tag_in=address[DATAWIDTH-1:INDEX];
   assign 		      Index=address[INDEX-1:0];
   assign 		      Valid_in=(Index < 4096) ? 1 : 0;
initial
begin
  for (i=0;i<8192;i=i+1)
  begin
    use_times[i]=0;
  end
end

	always @(vivi)
	begin
	  if (address<=262144)
	  begin
	  //save_outp=outp;
		if (reset == 1'b1)//0 OR 1?
	    begin
			for (i=0;i<8192;i=i+1)
			begin
				memcache[i]<=0;
				use_times[i]<=0;
			end
	    end
		else
		begin
			temp=memcache[Index];
			Valid=temp[53];
			temp2=memcache[Index+4096];
			Valid2=temp2[53];
			if (read==1'b1)
			begin
				if (Tag_in==temp[51:32] && Valid==1)
				begin
					data_out=temp[31:0];
					use_times[Index]=use_times[Index]+1;
					hit=1;
					dirty=0;
				end
				else if (Tag_in==temp2[51:32] && Valid2==1)
				begin
					data_out=temp2[31:0];
					use_times[Index+4096]=use_times[Index+4096]+1;
					hit=1;
					dirty=0;
				end
				else
				begin
					hit=0;
					if (use_times[Index]<use_times[Index+4096])
					begin
					  dirty=temp[52];
						data_out=temp[31:0];
						//memcache(Index)[51:32]=address[31:12];
						temp[51:32]=address[31:12];
						//memcache(Index)[31:0]=memData;
						temp[31:0]=save_outp;
						data_out=save_outp;
						temp[53]=1;
						temp[52]=0;
						memcache[Index]=temp;
						use_times[Index]=1;
					end
					else
					begin
					  dirty=temp2[52];
						data_out=temp2[31:0];
						//memcache(Index)[51:32]=address[31:12];
						temp2[51:32]=address[31:12];
						//memcache(Index)[31:0]=memData;
						temp2[31:0]=save_outp;
						data_out=save_outp;
						temp2[53]=1;
						temp2[52]=0;
						memcache[Index+4096]=temp2;
						use_times[Index+4096]=1;
					end
				end
			end
			else if (write==1'b1)
			begin
				if (Tag_in==temp[51:32] && Valid==1)
				begin
					if (data_in!=temp[31:0]) temp[52]=1;
					else temp[52]=0;
					//memcache(Index)[31:0]=memData;
					temp[31:0]=data_in;
					temp[53]=1;
					use_times[Index]=use_times[Index]+1;
					hit=1;
				end
				else if (Tag_in==temp2[51:32] && Valid2==1)
				begin
					if (data_in!=temp2[31:0]) temp2[52]=1;
					else temp2[52]=0;
					temp2[31:0]=data_in;
					temp2[53]=1;
					use_times[Index+4096]=use_times[Index+4096]+1;
					hit=1;
				end
				else
				begin
					hit=0;
				end
			end
			else hit=0;
			data_out=save_outp;
		end
		end
	end
	
endmodule