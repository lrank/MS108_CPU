module cpu(
  clk_i
  );

input wire clk_i;
wire isstall1;

PC PC(
  .clk_i  (clk_i),
  .control_i  (EX_MEM.control_o),
  .pc_i (EX_MEM.data1_o),
  .pc_o (instr_fetch.pc),
  .isstall  (isstall1)
  );

instr_fetch instr_fetch(
  .clk(clk_i),
  .pc (PC.pc_o),
  .instr_o  (IF_ID.IR_i),
  .isstall_i  (isstall1)
);

IF_ID IF_ID(
  .clk_i  (clk_i),
  .IR_i  (instr_fetch.instr_o),
  .IR_o  (regis.IR_i),
  .isstall_i  (hazard.isstall2_o)
);

wire [31:0] save_writedata;

Regis regis(
  .IR_i   (IF_ID.IR_o),
  .writenum_i (MEM_WB.reg_num),
  .writedata_i (save_writedata),
  .data1_o  (ID_EX.data1_i),
  .data2_o  (ID_EX.data2_i)
  );

SIGN_EXTEND extend(
  .IR_i   (IF_ID.IR_o),
  .data_o (ID_EX.data3_i)
);

ID_EX ID_EX(
  .clk_i    (clk_i),
  .data1_i  (regis.data1_o),
  .data2_i  (regis.data2_o),
  .data3_i  (extend.data_o),
  .data1_o  (ALU.src1),
  .data2_o  (MUX2.data1_i),
  .data3_o  (MUX2.data2_i),
  .control_o (MUX2.select_i),
  .op_o     (ALU.op),
  .IR_i     (IF_ID.IR_o),
  .IR_o     (EX_MEM.IR_i),
  .is_i     (bypass.is_o),
  .data_i   (bypass.data_i)
);

MUX32 MUX2(
  .data1_i   (ID_EX.data2_o),
  .data2_i   (ID_EX.data3_o),
  .select_i  (ID_EX.control_o),
  .data_o    (ALU.src2)
);

ALU ALU(
  .src1  (ID_EX.data1_i),
  .src2  (MUX2.data_o),
  .op     (ID_EX.op_o),
  .result   (EX_MEM.data1_i)
);

EX_MEM EX_MEM(
  .clk_i    (clk_i),
  .data1_i  (ALU.result),
  .data2_i  (ID_EX.data2_o),
  .IR_i     (ID_EX.IR_o),
  .data1_o  (MEM.address_i),
  .data2_o  (MEM.data_i),
  .IR_o     (MEM_WB.IR_i),
  .control_o(PC.control_i),
  .row_o    (MEM.row_i)
);

MEM MEM(
  .clk(clk_i),
  .address_i  (EX_MEM.data1_o),
  .data_i     (EX_MEM.data2_o),
  .row_i      (EX_MEM.row_o),
  .data_o     (MEM_WB.data1_i)
);

wire [31:0] save_data1_o;
wire [31:0] save_data2_o;
wire save_control_o;

MEM_WB MEM_WB(
  .clk_i    (clk_i),
  .data1_i  (MEM.data_o),
  .data2_i  (EX_MEM.data1_o),
  .IR_i     (EX_MEM.IR_o),
  .IR_o     (bypass.ir2_i),
  .data1_o  (save_data1_o),
  .data2_o  (save_data2_o),
  .control_o(save_control_o),
  .reg_num  (regis.writenum_i)
);

MUX32 MUX3(
  .data1_i   (save_data1_o),
  .data2_i   (save_data2_o),
  .select_i  (save_control_o),
  .data_o    (save_writedata)
);

hazard hazard(
  .clk_i (clk_i),
  .ir1_i (instr_fetch.instr_o),
  .ir2_i (IF_ID.IR_o),
  .ir3_i (ID_EX.IR_o),
  .isstall1_o  (isstall1),
  .isstall2_o  (IF_ID.isstall_i)
  );

bypass bypass(
  .ir1_i  (ID_EX.IR_o),
  .ir2_i  (MEM_WB.IR_o),
  .data_i (MUX3.data_o),
  .is_o   (ID_EX.is_i),
  .data_o (ID_EX.data_i)
);

endmodule