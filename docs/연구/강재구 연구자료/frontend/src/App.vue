<template>
  <v-app id="inspire">
    <!-- Section (왼쪽) -->
    <v-navigation-drawer
      v-model="drawer"
      app
      clipped
      width="25%"
    >
      <v-tabs
        background-color="gray"
        dark
        grow
      >
        <v-tab>
          one
        </v-tab>
        <v-tab>
          two
        </v-tab>
        <v-tab>
          three
        </v-tab>
        <v-tab>
          four
        </v-tab>
      </v-tabs>
      <!-- <v-list dense>
        <v-list-item link>
          <v-list-item-action>
            <v-icon>mdi-view-dashboard</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>Dashboard</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item link>
          <v-list-item-action>
            <v-icon>mdi-cog</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>Settings</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list> -->
    </v-navigation-drawer>

    <!-- Navbar -->
    <v-app-bar
      app
      clipped-left
      elevation=12
    >
      <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title>Waple</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items class="hidden-sm-and-down">
        <v-btn flat>One</v-btn>
        <v-btn flat>Two</v-btn>
        <v-btn flat>로그아웃</v-btn>
      </v-toolbar-items>
    </v-app-bar>

    <!-- Map 지도부분 -->
    <v-main>
      <v-container
        class="fill-height"
        fluid
      >
        <v-row
          align="center"
          justify="center"
        >
          <v-col class="shrink">
            <v-tooltip right>
              <template v-slot:activator="{ on }">
                <v-btn
                  :href="source"
                  icon
                  large
                  target="_blank"
                  v-on="on"
                >
                  <v-icon large>mdi-code-tags</v-icon>
                </v-btn>
              </template>
              <span>Source</span>
            </v-tooltip>
          </v-col>
        </v-row>
      </v-container>
    </v-main>

    <!-- Footer -->
    <v-footer app>
      <span>&copy; {{ new Date().getFullYear() }}</span>
    </v-footer>
  </v-app>
</template>

<script>
export default {
  props: {
    source: String,
  },
  data: () => ({
    drawer: null,
  }),
  created() {
    this.$vuetify.theme.dark = false;
  },
};
</script>
