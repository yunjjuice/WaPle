<template>
<v-container fill-width class="white lighten-4 elevation-2" style="height: 15vh;">
  <v-card-text>
    <v-container>
      <v-layout>
        <v-flex fluid>
          <v-select
          label="Filter"
          :items="userThemes"
          chips
          v-model="selectedThemes"
          item-text="name"
          item-value="type"
          max-height="auto"
          dense
          solo
          autocomplete
          multiple
          deletable-chips
          @change="selecting"
          >
            <template slot="item" slot-scope="data">
              <template>
                <v-list-item-avatar>
                  <img v-bind:src="data.item.avatar"/>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title v-text="data.item.name"></v-list-item-title>
                </v-list-item-content>
              </template>
            </template>
          </v-select>
        </v-flex>
      </v-layout>
    </v-container>
  </v-card-text>
</v-container>
</template>
<script>
import store from '@/store/index';
import EventBus from '@/utils/EventBus';

export default {
  data() {
    return {
      selectedThemes: [],
      userThemes: [],
      isSelected: false,
    };
  },
  computed: {
    userGroupTheme: () => store.getters.groupsThemes,
  },
  mounted() {
    const temp = this.userGroupTheme;
    const init = 0;
    for (let index = 0; index < temp.length; index += 1) {
      let innerData = {};
      innerData.header = temp[index].groupNames[init];
      this.userThemes.push(innerData);
      for (let index2 = 0; index2 < temp[index].themeIds.length; index2 += 1) {
        innerData = {};
        innerData.name = temp[index].themeNames[index2];
        innerData.type = { groupId: temp[index].groupId, themeId: temp[index].themeIds[index2] };
        this.userThemes.push(innerData);
      }
      innerData = {};
      if (index !== temp.length - 1) {
        innerData.divider = true;
        this.userThemes.push(innerData);
      }
    }
  },
  methods: {
    selecting() {
      EventBus.$emit('userSelect', this.selectedThemes);
    },
    finding(groupTheme, inputData) {
      return groupTheme.groupId === inputData;
    },
  },
};
</script>

<style>

</style>
