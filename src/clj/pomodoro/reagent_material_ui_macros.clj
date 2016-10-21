(ns pomodoro.reagent-material-ui-macros)

(def material-tags
  '[AppBar
    Subheader
    AutoComplete
    Avatar
    Badge
    Card
    CardActions
    CardHeader
    CardMedia
    CardText
    CardTitle
    Checkbox
    CircularProgress
    DatePicker
    Dialog
    Divider
    Drawer
    DropDownMenu
    FlatButton
    FloatingActionButton
    FontIcon
    GridList
    GridTile
    IconButton
    IconMenu
    LinearProgress
    List
    ListItem
    Menu
    MenuItem
    Paper
    Popover
    RadioButton
    RadioButtonGroup
    RaisedButton
    RefreshIndicator
    SelectField
    Slider
    SvgIcon
    Step
    StepButton
    StepContent
    StepLabel
    Stepper
    Snackbar
    Tab
    Tabs
    Table
    TableBody
    TableFooter
    TableHeader
    TableHeaderColumn
    TableRow
    TableRowColumn
    Toggle
    TimePicker
    TextField
    Toolbar
    ToolbarGroup
    ToolbarSeparator
    ToolbarTitle])


(def icon-tags '[NavigationMoreVert
                 ActionAlarm
                 ActionCheckCircle
                 ActionPowerSettingsNew])


(defn material-ui-react-import [tname]
  `(def ~tname
     (reagent.core/adapt-react-class (aget js/MaterialUI ~(name tname)))))


(defn material-ui-icon-import [tname]
  `(def ~tname
     (reagent.core/adapt-react-class (aget js/MaterialUISvgIcons ~(name tname)))))


(defmacro export-material-ui-react-classes[]
  `(do
     ~@(map material-ui-react-import material-tags)
     ))


(defmacro export-material-ui-icon-classes[]
  `(do
     ~@(map material-ui-icon-import icon-tags)
     ))


