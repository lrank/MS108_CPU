module cpu(
  clk_i
  );

input wire clk_i;

PC PC(
  .clk_i  (clk_i),
  .control_i  (EX_MEM.control_o),
  .pc_i (EX_MEM.data1_o),
  .pc_o (instr_fetch.pc_i)
  );

instr_fetch instr_fetch(
  .pc (PC.pc_o),
  .IR_o  (IF_ID.IR_i)
);

IF_ID IF_ID(
  .clk_i  (clk_i),
  .IR_i  (instr_fetch.IR_o),
  .IR_o  (register.IR_i)
);

Register register(
  .IR_i   (IF_ID.IR_o),
  .writenum_i (),
  .writedata_i (),
  .data1_o  (ID_EX.data1_i),
  .data2_o  (ID_EX.data2_i)
  );

SIGN_EXTEND extend(
  .IR_i   (IF_ID.IR_o),
  .data_o (ID_EX.data3_i)
);

ID_EX ID_EX(
  .clk_i    (clk_i),
  .data1_i  (register.data1_o),
  .data2_i  (register.data2_o),
  .data3_i  (extend.data_o),
  .data1_o  (ALU.data1_i),
  .data2_o  (MUX2.data1_i),
  .data3_o  (MUX2.data2_i),
  .control_o (MUX2.select_i),
  .op_o     (ALU.op_i),
  .IR_i     (IF_ID.IR_o),
  .IR_o     ()
);

MUX32 MUX2(
  .data1_i   (ID_EX.data2_o),
  .data2_i   (ID_EX.data3_o),
  .select_i  (ID_EX.control),
  .data_o    (ALU.data2_i)
);

ALU ALU(
  .data1_i  (ID_EX.data1_i),
  .data2_i  (MUX2.data_o),
  .op_i     (ID_EX.op_o),
  .data_o   (EX_MEM.data1_i)
);

EX_MEM EX_MEM(
  .clk_i    (clk_i),
  .data1_i  (ALU.data_o),
  .data2_i  (ID_EX.data2_o),
  .IR_i     (ID_EX.IR_o),
  .data1_o  (MEM.address_i),
  .data2_o  (MEM.data_i),
  .IR_o     (MEM_WB.IR_i),
  .control_o(PC.control_i),
  .row_o    (MEM.row_i)
);

MEM MEM(
  .address_i  (EX_MEM.data1_o),
  .data_i     (EX_MEM.data2_o),
  .row_i      (EX_MEM.row_o),
  .data_o     (MEM_WB.data1_i)
);

MEM_WB MEM_WB(
  .clk_i    (clk_i),
  .data1_i  (MEM.data_o),
  .data2_i  (EX_MEM.data1_o),
  .IR_i     (EX_MEM.IR_o),
  .data1_o  (MUX3.data1_i),
  .data2_o  (MUX3.data2_i),
  .control_o(MUX3.select_i),
  .reg_num  (register.writenum_i)
);

MUX32 MUX3(
  data1_i   (MEM_WB.data1_o),
  data2_i   (MEM_WB.data2_o),
  select_i  (MEM_WB.control_o),
  data_o    (register.writedata_i)
);

endmodule