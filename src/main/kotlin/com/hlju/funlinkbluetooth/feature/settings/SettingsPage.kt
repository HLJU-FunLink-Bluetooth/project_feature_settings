package com.hlju.funlinkbluetooth.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Dp
import com.hlju.funlinkbluetooth.core.designsystem.navigation.PageScaffold
import com.hlju.funlinkbluetooth.core.designsystem.token.Spacing
import com.hlju.funlinkbluetooth.core.designsystem.token.adaptivePageHorizontalPadding
import com.hlju.funlinkbluetooth.core.designsystem.widget.SectionTitle
import com.hlju.funlinkbluetooth.core.designsystem.widget.StateMessageCard
import com.hlju.funlinkbluetooth.core.designsystem.widget.SurfaceTone
import com.hlju.funlinkbluetooth.core.preferences.AppThemeMode
import top.yukonga.miuix.kmp.basic.MiuixScrollBehavior
import top.yukonga.miuix.kmp.basic.TabRowWithContour
import top.yukonga.miuix.kmp.utils.overScrollVertical
import top.yukonga.miuix.kmp.utils.scrollEndHaptic

@Composable
fun SettingsPage(
    bottomInset: Dp,
    themeMode: AppThemeMode,
    onThemeModeChange: (AppThemeMode) -> Unit,
) {
    val scrollBehavior = MiuixScrollBehavior()
    val pageHorizontalPadding = adaptivePageHorizontalPadding(Spacing.PageOuterInset)
    val themeModes = remember { AppThemeMode.entries.toList() }
    val selectedIndex = themeModes.indexOf(themeMode).coerceAtLeast(0)

    PageScaffold(
        title = "设置",
        scrollBehavior = scrollBehavior,
    ) { innerPadding, contentModifier ->
        LazyColumn(
            modifier = contentModifier
                .fillMaxWidth()
                .fillMaxHeight()
                .scrollEndHaptic()
                .overScrollVertical()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .padding(horizontal = pageHorizontalPadding),
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding() + Spacing.PageSectionGap,
                bottom = innerPadding.calculateBottomPadding() + bottomInset + Spacing.PageSectionGap,
            ),
            verticalArrangement = Arrangement.spacedBy(Spacing.PageSectionGap),
            overscrollEffect = null
        ) {
            item {
                SectionTitle(
                    title = "界面偏好",
                    summary = "主题切换会立即生效，保留现有系统/浅色/深色三种模式。"
                )
            }
            item {
                TabRowWithContour(
                    tabs = themeModes.map { it.displayName() },
                    selectedTabIndex = selectedIndex,
                    onTabSelected = { index ->
                        val nextMode = themeModes.getOrNull(index) ?: return@TabRowWithContour
                        if (nextMode != themeMode) {
                            onThemeModeChange(nextMode)
                        }
                    }
                )
            }
            item {
                StateMessageCard(
                    title = "当前模式：${themeMode.displayName()}",
                    summary = when (themeMode) {
                        AppThemeMode.System -> "跟随系统明暗切换，适合日常使用。"
                        AppThemeMode.Light -> "固定为浅色界面，便于在明亮环境下查看。"
                        AppThemeMode.Dark -> "固定为深色界面，夜间使用更稳定。"
                    },
                    tone = SurfaceTone.Primary
                )
            }
        }
    }
}
