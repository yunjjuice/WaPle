<template>
  <div class="text-center">
    <v-dialog
      v-model="dialog"
      max-width="600px"
    >
      <!-- Modal 버튼부분 -->
      <!-- v-slot:activator 의 의미를 모르겠음.. -->
      <template v-slot:activator="{ on, attrs }">
        <v-expansion-panel class="mt-4 border border-primary">
          <v-expansion-panel-header
            dark
            v-bind="attrs"
            v-on="on"
            style="color: red;"
          >
            그룹 추가하기
          </v-expansion-panel-header>
        </v-expansion-panel>
      </template>

      <!-- Modal 안쪽 내용 -->
      <v-card>
        <v-card-title>
          <span class="headline">그룹 추가하기</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field label="그룹명*"  hint="그룹이름을 입력하세요." persistent-hint required />
              </v-col>
            </v-row>
          </v-container>
          <small>*필수 입력항목</small>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="dialog = false">Close</v-btn>
          <v-btn color="blue darken-1" text @click="dialog = false">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      dialog: false,
    };
  },
};
</script>
